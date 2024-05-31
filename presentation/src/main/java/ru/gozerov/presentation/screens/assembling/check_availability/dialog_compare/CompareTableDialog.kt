package ru.gozerov.presentation.screens.assembling.check_availability.dialog_compare

import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout.LayoutParams
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.navArgs
import ru.gozerov.presentation.databinding.DialogCompareTableBinding
import ru.gozerov.presentation.screens.assembling.check_availability.camera.CheckAvailabilityFragment

class CompareTableDialog : DialogFragment() {

    private lateinit var binding: DialogCompareTableBinding

    private val args: CompareTableDialogArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogCompareTableBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.window?.setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        binding.foundComponentsList.adapter = ComponentArrayAdapter(
            requireContext(),
            args.foundComponents.map { it.name }.toTypedArray()
        )
        binding.neededComponentsList.adapter = ComponentArrayAdapter(
            requireContext(),
            args.neededComponents.map { it.name }.toTypedArray()
        )
        binding.dismissButton.setOnClickListener {
            parentFragmentManager.setFragmentResult(
                CheckAvailabilityFragment.REQUEST_KEY, bundleOf()
            )
            dialog?.dismiss()
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        parentFragmentManager.setFragmentResult(
            CheckAvailabilityFragment.REQUEST_KEY, bundleOf()
        )
        super.onDismiss(dialog)
    }

}