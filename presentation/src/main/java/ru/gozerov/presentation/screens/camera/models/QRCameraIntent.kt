package ru.gozerov.presentation.screens.camera.models

sealed interface QRCameraIntent {

    object Navigate: QRCameraIntent

    class ShowComponent(
        val id: Int
    ) : QRCameraIntent

    class ShowContainer(
        val number: String
    ) : QRCameraIntent

    class SetCameraActive(
        val isActive: Boolean
    ) : QRCameraIntent

    data class ShowError(
        val message: String
    ) : QRCameraIntent


}