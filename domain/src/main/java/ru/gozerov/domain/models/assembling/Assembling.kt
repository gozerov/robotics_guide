package ru.gozerov.domain.models.assembling

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import ru.gozerov.domain.models.login.User

@Parcelize
data class Assembling(
    val id: Int,
    val name: String,
    val containers: List<Container>,
    val fileLink: String,
    val instructionLink: String,
    val user: User?,
    val readyAmount: Int
) : Parcelable