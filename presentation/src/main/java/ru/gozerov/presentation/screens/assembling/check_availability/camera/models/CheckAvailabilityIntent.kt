package ru.gozerov.presentation.screens.assembling.check_availability.camera.models

sealed interface CheckAvailabilityIntent {

    class AddComponent(
        val id: Int
    ): CheckAvailabilityIntent

    class SetCameraActive(
        val isActive: Boolean
    ): CheckAvailabilityIntent

    data class ShowError(
        val message: String
    ): CheckAvailabilityIntent

}