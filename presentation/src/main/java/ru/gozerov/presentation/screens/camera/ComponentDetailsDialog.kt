package ru.gozerov.presentation.screens.camera

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ru.gozerov.presentation.R
import ru.gozerov.presentation.databinding.DialogComponentDetailsBinding

class ComponentDetailsDialog : BottomSheetDialogFragment() {

    private lateinit var binding: DialogComponentDetailsBinding

    private val args by navArgs<ComponentDetailsDialogArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogComponentDetailsBinding.inflate(inflater, container, false)
        binding.txtComponentName.text = args.component.name
        binding.txtComponentRoom.text = getString(R.string.storing_in, args.component.room)
        binding.txtId.text = getString(R.string.simple_id_is, args.component.id)
        return binding.root
    }

    override fun onDismiss(dialog: DialogInterface) {
        parentFragmentManager.setFragmentResult(QRCameraFragment.REQUEST_KEY, bundleOf())
        super.onDismiss(dialog)
    }

}