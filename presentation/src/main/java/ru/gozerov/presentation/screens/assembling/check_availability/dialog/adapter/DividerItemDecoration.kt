package ru.gozerov.presentation.screens.assembling.check_availability.dialog.adapter

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView


class DividerItemDecoration(
    context: Context,
    resId: Int
) : RecyclerView.ItemDecoration() {

    private var mDivider: Drawable = ContextCompat.getDrawable(context, resId)!!

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)

        for (i in 0 until parent.childCount) {

            if (i != parent.childCount - 1) {
                val child: View = parent.getChildAt(i)

                val params = child.layoutParams as RecyclerView.LayoutParams
                val dividerTop: Int = child.bottom + params.bottomMargin
                val dividerBottom: Int = dividerTop + mDivider.intrinsicHeight

                mDivider.setBounds(0, dividerTop, parent.width, dividerBottom)
                mDivider.draw(c)
            }
        }
    }
}