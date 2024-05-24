package ru.gozerov.presentation.screens.camera

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.gozerov.presentation.R
import ru.gozerov.presentation.databinding.FragmentQrCameraBinding
import ru.gozerov.presentation.screens.camera.models.QRCameraEffect
import ru.gozerov.presentation.screens.camera.models.QRCameraIntent

@AndroidEntryPoint
class QRCameraFragment : Fragment() {

    private lateinit var binding: FragmentQrCameraBinding

    private val viewModel: QRCameraViewModel by viewModels()

    @SuppressLint("RestrictedApi")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentQrCameraBinding.inflate(inflater, container, false)

        parentFragmentManager.setFragmentResultListener(
            REQUEST_KEY, viewLifecycleOwner
        ) { _, _ -> viewModel.handleIntent(QRCameraIntent.SetCameraActive(true)) }

        binding.barCodeView.statusView.text = ""

        binding.barCodeView.barcodeView.decodeContinuous { result ->
            if (viewModel.isCameraActive && findNavController().currentDestination == findNavController().findDestination(
                    R.id.nav_camera
                )
            ) {
                try {
                    val id = result.text.toIntOrNull()
                    id?.let {
                        viewModel.handleIntent(QRCameraIntent.ShowComponent(id))
                        //viewModel.handleIntent(QRCameraIntent.ShowContainer(result.text))
                        viewModel.handleIntent(QRCameraIntent.SetCameraActive(false))
                    } ?: viewModel.handleIntent(QRCameraIntent.ShowError("message"))
                } catch (e: Exception) {
                    viewModel.handleIntent(QRCameraIntent.ShowError(id.toString()))
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.effect.collect { effect ->
                    when (effect) {
                        is QRCameraEffect.None -> {}
                        is QRCameraEffect.ShowComponent -> {
                            val action =
                                QRCameraFragmentDirections.actionNavCameraToComponentDetailsDialog(
                                    effect.component
                                )
                            viewModel.handleIntent(QRCameraIntent.Navigate)
                            findNavController().navigate(action)
                        }

                        is QRCameraEffect.ShowContainer -> {
                            val action =
                                QRCameraFragmentDirections.actionNavCameraToContainerDetailsDialog(
                                    effect.container
                                )
                            findNavController().navigate(action)
                        }

                        is QRCameraEffect.Error -> {
                            Snackbar.make(
                                requireView(),
                                getString(R.string.unknown_error), Snackbar.LENGTH_SHORT
                            )
                                .show()
                            viewModel.handleIntent(QRCameraIntent.SetCameraActive(true))
                        }
                    }
                }
            }
        }

        binding.navUp.setOnClickListener {
            findNavController().popBackStack()
        }
        return binding.root
    }

    override fun onResume() {
        binding.barCodeView.resume()
        super.onResume()
    }

    override fun onPause() {
        binding.barCodeView.pause()
        super.onPause()
    }

    companion object {

        const val REQUEST_KEY = "requestKey"

    }

}