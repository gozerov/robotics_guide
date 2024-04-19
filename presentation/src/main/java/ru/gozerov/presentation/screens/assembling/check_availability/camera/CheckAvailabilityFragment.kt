package ru.gozerov.presentation.screens.assembling.check_availability.camera

import android.annotation.SuppressLint
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
import kotlin.random.Random

class CheckAvailabilityFragment : Fragment() {

    private lateinit var binding: FragmentQrCameraBinding

    private val viewModel: CheckAvailabilityViewModel by viewModels()

    @SuppressLint("RestrictedApi")
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
            if (viewModel.isCameraActive && findNavController().currentDestination == findNavController()
                    .findDestination(R.id.checkAvailabilityFragment)
            ) {
                viewModel.addComponent(
                    Component(
                        Random.nextInt(0, 100),
                        "Винт М3-20",
                        null,
                        "A-415"
                    )
                )
                val action =
                    CheckAvailabilityFragmentDirections.actionCheckAvailabilityFragmentToCheckAvailabilityDialog(
                        viewModel.components
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

        const val REQUEST_KEY = "checkRequestKey"

    }


}