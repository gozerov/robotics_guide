package ru.gozerov.presentation.screens.assembling.details.models

import ru.gozerov.domain.models.assembling.Assembling

sealed interface AssemblingDetailsViewState {

    object Empty: AssemblingDetailsViewState

    class LoadedAssembling(
        val assembling: Assembling
    ): AssemblingDetailsViewState

    class Error: AssemblingDetailsViewState

}