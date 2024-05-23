package ru.gozerov.presentation.screens.camera.edit.edit_component.models

sealed interface EditComponentEffect {

    object None: EditComponentEffect

    object Error: EditComponentEffect

    object ShowDialog: EditComponentEffect

}