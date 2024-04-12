package ru.gozerov.presentation.screens.assembling.list.models

import ru.gozerov.domain.models.assembling.SimpleAssembling

sealed interface AssemblingListViewState {

    object Empty: AssemblingListViewState

    class LoadedAssemblings(
        val newAssemblings: List<SimpleAssembling>,
        val allAssemblings: List<SimpleAssembling>
    ): AssemblingListViewState

    object Error: AssemblingListViewState

}