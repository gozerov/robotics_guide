package ru.gozerov.presentation.screens.assembling.assembly_process.models

import ru.gozerov.domain.models.assembling.AssemblyStep

sealed interface AssemblyProcessViewState {

    object Empty : AssemblyProcessViewState

    class LoadedStep(
        val step: AssemblyStep
    ) : AssemblyProcessViewState

    class Error : AssemblyProcessViewState

}