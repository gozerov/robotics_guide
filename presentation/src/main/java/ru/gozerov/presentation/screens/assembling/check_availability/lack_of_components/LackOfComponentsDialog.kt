package ru.gozerov.presentation.screens.assembling.check_availability.lack_of_components

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ru.gozerov.presentation.databinding.DialogLackOfComponentsBinding

class LackOfComponentsDialog : DialogFragment() {

    private lateinit var binding: DialogLackOfComponentsBinding

    private val navArgs: LackOfComponentsDialogArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogLackOfComponentsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.window?.setLayout(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        binding.continueButton.setOnClickListener {
            val action =
                LackOfComponentsDialogDirections.actionLackOfComponentsDialogToAssemblyProcessFragment(
                    navArgs.assemblingId
                )
            findNavController().navigate(action)
        }
        binding.dismissButton.setOnClickListener {
            dialog?.dismiss()
        }
    }

}