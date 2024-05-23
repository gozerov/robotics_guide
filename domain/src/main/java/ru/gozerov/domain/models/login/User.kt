package ru.gozerov.domain.models.login

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val id: String,
    val firstName: String,
    val lastName: String,
    val middleName: String,
    val email: String,
    val phoneNumber: String
) : Parcelable