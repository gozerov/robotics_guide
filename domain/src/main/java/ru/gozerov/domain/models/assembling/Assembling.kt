package ru.gozerov.domain.models.assembling

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Assembling(
    val id: Int,
    val name: String,
    val instruction: String,
    val readyAmount: Int,
    val linkToProject: String,
    val linkToSound: String?,
    val userId: Int,
    val components: List<AssemblingComponent>
) : Parcelable