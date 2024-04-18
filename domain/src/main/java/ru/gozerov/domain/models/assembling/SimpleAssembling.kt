package ru.gozerov.domain.models.assembling

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SimpleAssembling(
    val id: Int,
    val name: String
) : Parcelable
