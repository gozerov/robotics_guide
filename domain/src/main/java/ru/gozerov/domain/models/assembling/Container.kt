package ru.gozerov.domain.models.assembling

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Container(
    val number: String,
    val room: String,
    val amount: Int,
    val componentId: Int
): Parcelable
