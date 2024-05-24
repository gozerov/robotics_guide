package ru.gozerov.presentation.screens.camera.edit.edit_container.models

import ru.gozerov.domain.models.assembling.Container

sealed interface EditContainerIntent {

    class SaveChanges(val container: Container) : EditContainerIntent

    class ShowError: EditContainerIntent

}