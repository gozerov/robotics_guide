package ru.gozerov.data.login.models

data class LoginRequestBody(
    val name: String,
    val lastname: String,
    val role: Int,
    val email: String,
    val phone: String
)
