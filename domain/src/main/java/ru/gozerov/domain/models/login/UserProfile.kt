package ru.gozerov.domain.models.login

data class UserProfile(
    val id: String,
    val firstName: String,
    val lastName: String,
    val imageUrl: String?,
    val role: Int
)
