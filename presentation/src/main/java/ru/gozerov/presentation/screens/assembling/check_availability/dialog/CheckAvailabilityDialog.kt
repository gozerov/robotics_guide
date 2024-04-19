package ru.gozerov.presentation.screens.assembling.check_availability.dialog

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ru.gozerov.domain.models.assembling.TableData
import ru.gozerov.domain.models.assembling.TableTitle
import ru.gozerov.presentation.R
import ru.gozerov.presentation.databinding.DialogCheckAvailabilityBinding
import ru.gozerov.presentation.screens.assembling.check_availability.camera.CheckAvailabilityFragment
import ru.gozerov.presentation.screens.assembling.check_availability.dialog.adapter.ComponentListAdapter

class CheckAvailabilityDialog : BottomSheetDialogFragment() {

    private lateinit var binding: DialogCheckAvailabilityBinding

    private val adapter = ComponentListAdapter()

    private val args by navArgs<CheckAvailabilityDialogArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogCheckAvailabilityBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val dividerItemDecoration =
            DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL)
        dividerItemDecoration.setDrawable(
            ContextCompat.getDrawable(
                requireContext(),
                R.drawable.divider_component_list
            )!!
        )
        binding.componentsList.addItemDecoration(
            ru.gozerov.presentation.screens.assembling.check_availability.dialog.adapter.DividerItemDecoration(
                requireContext(),
                R.drawable.divider_component_list
            )
        )
        val data = mutableListOf<TableData>()
        data.add(TableTitle("Название детали"))
        data.addAll(args.components)
        adapter.data = data
        binding.componentsList.adapter = adapter
    }

    override fun onDismiss(dialog: DialogInterface) {
        parentFragmentManager.setFragmentResult(CheckAvailabilityFragment.REQUEST_KEY, bundleOf())
        super.onDismiss(dialog)
    }


}