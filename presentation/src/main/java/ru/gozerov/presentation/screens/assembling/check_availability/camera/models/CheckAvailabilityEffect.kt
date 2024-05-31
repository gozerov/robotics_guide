package ru.gozerov.presentation.screens.assembling.check_availability.camera.models

import ru.gozerov.domain.models.assembling.Component

sealed interface CheckAvailabilityEffect {

    data object None: CheckAvailabilityEffect

    data class ShowCheckAvailabilityDialog(
        val components: List<Component>
    ): CheckAvailabilityEffect

    class ShowAllComponentsDialog(
        val components: List<Component>
    ): CheckAvailabilityEffect

    class NavigateToProcess: CheckAvailabilityEffect

    class NavigateToLackOfComponentsDialog: CheckAvailabilityEffect

    data class Error(
        val message: String
    ): CheckAvailabilityEffect

}