package ru.gozerov.domain.models.assembling

data class AssemblyStep(
    val container: Container,
    val step: Int,
    val stepCount: Int,
    val isFinish: Boolean = false
)
