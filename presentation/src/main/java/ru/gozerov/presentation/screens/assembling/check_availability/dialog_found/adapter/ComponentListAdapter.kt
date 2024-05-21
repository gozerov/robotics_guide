package ru.gozerov.presentation.screens.assembling.check_availability.dialog_found.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import ru.gozerov.presentation.R
import ru.gozerov.domain.models.assembling.TableData
import ru.gozerov.domain.models.assembling.TableTitle
import ru.gozerov.presentation.databinding.ItemColumnTitleBinding
import ru.gozerov.presentation.databinding.ItemSimpleComponentBinding
import ru.gozerov.presentation.screens.assembling.check_availability.dialog_found.ComponentWithNeed

class ComponentListAdapter : RecyclerView.Adapter<ComponentListAdapter.TableViewHolder>() {

    abstract class TableViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        abstract fun bind(data: TableData)

    }

    class ComponentViewHolder(private val binding: ItemSimpleComponentBinding) :
        TableViewHolder(binding.root) {

        override fun bind(data: TableData) {
            val component = data as ComponentWithNeed
            binding.root.tag = component
            binding.txtComponentName.text = component.component.name
            if (component.isNeeded)
                binding.txtComponentName.background =
                    ContextCompat.getDrawable(binding.root.context, R.drawable.bg_needed_component)
            else
                binding.txtComponentName.background =
                    ContextCompat.getDrawable(binding.root.context, R.drawable.bg_unneeded_component)
        }

    }

    class TitleViewHolder(private val binding: ItemColumnTitleBinding) :
        TableViewHolder(binding.root) {

        override fun bind(data: TableData) {
            binding.txtColumnTitle.text = (data as TableTitle).name
        }

    }

    override fun getItemViewType(position: Int): Int {
        return when (data[position]) {
            is TableTitle -> TYPE_TITLE
            is ComponentWithNeed -> TYPE_COMPONENT
            else -> error("Unknown viewType")
        }
    }

    var data: List<TableData> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TableViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            TYPE_TITLE -> TitleViewHolder(
                ItemColumnTitleBinding.inflate(
                    layoutInflater,
                    parent,
                    false
                )
            )

            TYPE_COMPONENT -> ComponentViewHolder(
                ItemSimpleComponentBinding.inflate(
                    layoutInflater,
                    parent,
                    false
                )
            )

            else -> error("Unknown viewType")
        }
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: TableViewHolder, position: Int) {
        holder.bind(data[position])
    }

    companion object {

        const val TYPE_TITLE = 0
        const val TYPE_COMPONENT = 1
    }

}