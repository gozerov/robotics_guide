package ru.gozerov.presentation.screens.assembling.check_availability.camera

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
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.gozerov.presentation.R
import ru.gozerov.presentation.databinding.FragmentQrCameraBinding
import ru.gozerov.presentation.screens.assembling.check_availability.camera.models.CheckAvailabilityEffect
import ru.gozerov.presentation.screens.assembling.check_availability.camera.models.CheckAvailabilityIntent
import ru.gozerov.presentation.screens.assembling.check_availability.dialog_found.ComponentWithNeed

@AndroidEntryPoint
class CheckAvailabilityFragment : Fragment() {

    private lateinit var binding: FragmentQrCameraBinding

    private val viewModel: CheckAvailabilityViewModel by viewModels()

    private val args: CheckAvailabilityFragmentArgs by navArgs()

    @SuppressLint("RestrictedApi")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentQrCameraBinding.inflate(inflater, container, false)

        parentFragmentManager.setFragmentResultListener(
            REQUEST_KEY, viewLifecycleOwner
        ) { _, _ -> viewModel.handleIntent(CheckAvailabilityIntent.SetCameraActive(true)) }

        binding.barCodeView.statusView.text = ""

        binding.barCodeView.barcodeView.decodeContinuous { result ->
            if (viewModel.isCameraActive && findNavController().currentDestination == findNavController()
                    .findDestination(R.id.checkAvailabilityFragment)
            ) {
                try {
                    val id = result.text.toInt()
                    viewModel.handleIntent(CheckAvailabilityIntent.AddComponent(id))
                    viewModel.handleIntent(CheckAvailabilityIntent.SetCameraActive(false))
                } catch (e: Exception) {
                    viewModel.handleIntent(CheckAvailabilityIntent.ShowError(result.text))
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.effect.collect { effect ->
                    when (effect) {
                        is CheckAvailabilityEffect.None -> {}
                        is CheckAvailabilityEffect.ShowDialog -> {
                            val components = effect.components.map { component ->
                                ComponentWithNeed(
                                    component,
                                    args.neededComponents.contains(component)
                                )
                            }
                            val action =
                                CheckAvailabilityFragmentDirections.actionCheckAvailabilityFragmentToCheckAvailabilityDialog(
                                    components.toTypedArray(),
                                    args.neededComponents,
                                    args.assemblingId
                                )
                            findNavController().navigate(action)
                        }

                        is CheckAvailabilityEffect.Error -> {
                            Snackbar.make(
                                requireView(),
                                getString(R.string.unknown_error), Snackbar.LENGTH_SHORT
                            )
                                .show()
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

        const val REQUEST_KEY = "checkRequestKey"

    }


}