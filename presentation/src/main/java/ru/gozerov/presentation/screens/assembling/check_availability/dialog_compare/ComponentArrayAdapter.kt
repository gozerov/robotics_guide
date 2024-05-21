package ru.gozerov.presentation.screens.assembling.check_availability.dialog_compare

import ru.gozerov.presentation.R
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView


class ComponentArrayAdapter(
    context: Context,
    components: Array<String>
): ArrayAdapter<String>(context, 0, components) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val text = getItem(position)
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_simple_component_2, parent, false)
        (view as TextView).text = text
        return view
    }

}