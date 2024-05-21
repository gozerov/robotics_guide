package ru.gozerov.domain.models.assembling

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Component(
    val id: Int,
    val name: String,
    val imageUrl: String?,
    val room: String
): Parcelable
