package ru.gozerov.domain.models.assembling

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AssemblingComponent(
    val componentId: Int,
    val name: String,
    val amount: Int,
    val photoUrl: String?,
    val linkToSound: String?
) : Parcelable