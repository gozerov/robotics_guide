package ru.gozerov.presentation.screens.assembling.check_availability.camera.models

import ru.gozerov.domain.models.assembling.Component

sealed interface CheckAvailabilityEffect {

    data object None: CheckAvailabilityEffect

    data class ShowDialog(
        val components: List<Component>
    ): CheckAvailabilityEffect

    data class Error(
        val message: String
    ): CheckAvailabilityEffect

}