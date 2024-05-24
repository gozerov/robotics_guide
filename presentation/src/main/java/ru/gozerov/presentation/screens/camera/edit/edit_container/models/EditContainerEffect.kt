package ru.gozerov.presentation.screens.camera.edit.edit_container.models

sealed interface EditContainerEffect {

    object None : EditContainerEffect

    object Error : EditContainerEffect

    object ShowDialog : EditContainerEffect

}