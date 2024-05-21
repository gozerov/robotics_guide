package ru.gozerov.presentation.screens.camera.models

import ru.gozerov.domain.models.assembling.Component
import ru.gozerov.domain.models.assembling.Container

sealed interface QRCameraEffect {

    data object None : QRCameraEffect

    data class ShowComponent(
        val component: Component
    ) : QRCameraEffect

    data class ShowContainer(
        val container: Container
    ) : QRCameraEffect

    data class Error(
        val message: String
    ) : QRCameraEffect

}