package ru.gozerov.presentation.screens.camera.dialogs

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ru.gozerov.presentation.R
import ru.gozerov.presentation.databinding.DialogContainerDetailsBinding
import ru.gozerov.presentation.screens.camera.QRCameraFragment
import ru.gozerov.presentation.screens.tabs.TabsFragmentDirections

class ContainerDetailsDialog : BottomSheetDialogFragment() {

    private lateinit var binding: DialogContainerDetailsBinding

    private val args by navArgs<ContainerDetailsDialogArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogContainerDetailsBinding.inflate(inflater, container, false)
        binding.txtId.text = getString(R.string.simple_id_is, args.container.componentId)
        binding.txtContainerName.text = args.container.number
        binding.txtDetailsCount.text = getString(R.string.details_count_is, args.container.amount)

        binding.editButton.setOnClickListener {
            val action = TabsFragmentDirections.actionTabsFragmentToEditContainerFragment(args.container)
            activity?.supportFragmentManager?.findFragmentById(R.id.globalFragmentContainer)?.findNavController()?.navigate(action)
        }
        return binding.root
    }

    override fun onDismiss(dialog: DialogInterface) {
        parentFragmentManager.setFragmentResult(QRCameraFragment.REQUEST_KEY, bundleOf())
        super.onDismiss(dialog)
    }

}