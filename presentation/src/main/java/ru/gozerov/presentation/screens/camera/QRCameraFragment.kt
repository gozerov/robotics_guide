package ru.gozerov.presentation.screens.camera

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.gozerov.domain.models.assembling.Component
import ru.gozerov.presentation.R
import ru.gozerov.presentation.databinding.FragmentQrCameraBinding

class QRCameraFragment : Fragment(R.layout.fragment_qr_camera) {

    private lateinit var binding: FragmentQrCameraBinding

    private val viewModel: QRCameraViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentQrCameraBinding.inflate(inflater, container, false)

        parentFragmentManager.setFragmentResultListener(
            REQUEST_KEY, viewLifecycleOwner
        ) { _, _ -> viewModel.isCameraActive = true }

        binding.barCodeView.resume()
        binding.barCodeView.statusView.text = ""

        binding.barCodeView.barcodeView.decodeContinuous {
            if (viewModel.isCameraActive) {
                val action = QRCameraFragmentDirections.actionNavCameraToComponentDetailsDialog(
                    Component(3, "Винт М3-20", null, "А-415")
                )
                findNavController().navigate(action)

                viewModel.isCameraActive = false
            }

        }
        binding.navUp.setOnClickListener {
            findNavController().popBackStack()
        }
        return binding.root
    }

    companion object {

        const val REQUEST_KEY = "requestKey"

    }

}