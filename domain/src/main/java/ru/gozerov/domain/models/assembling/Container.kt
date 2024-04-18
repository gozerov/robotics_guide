package ru.gozerov.domain.models.assembling

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Container(
    val id: Int,
    val component: Component,
    val amount: Int
): Parcelable
