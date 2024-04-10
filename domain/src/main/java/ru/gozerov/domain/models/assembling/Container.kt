package ru.gozerov.domain.models.assembling

data class Container(
    val id: Int,
    val component: Component,
    val amount: Int
)