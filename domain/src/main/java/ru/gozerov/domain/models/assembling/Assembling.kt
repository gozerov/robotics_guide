package ru.gozerov.domain.models.assembling

import ru.gozerov.domain.models.login.User

data class Assembling(
    val id: Int,
    val name: String,
    val containers: List<Container>,
    val fileLink: String,
    val instructionLink: String,
    val user: User?,
    val readyAmount: Int
)
