package ru.gozerov.domain.models.login

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val id: Int,
    val name: String,
    val lastName: String,
    val role: String,
    val email: String,
    val phone: String,
    val photo: String
) : Parcelable