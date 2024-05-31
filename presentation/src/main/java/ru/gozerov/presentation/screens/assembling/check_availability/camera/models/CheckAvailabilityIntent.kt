package ru.gozerov.presentation.screens.assembling.check_availability.camera.models

import ru.gozerov.domain.models.assembling.AssemblingComponent

sealed interface CheckAvailabilityIntent {

    class AddComponent(
        val id: Int
    ) : CheckAvailabilityIntent

    class SetCameraActive(
        val isActive: Boolean
    ) : CheckAvailabilityIntent

    data class ShowError(
        val message: String
    ) : CheckAvailabilityIntent

    class ShowAllComponentsDialog : CheckAvailabilityIntent

    class CalculateComponentsDiff(val neededComponents: List<AssemblingComponent>) :
        CheckAvailabilityIntent

}