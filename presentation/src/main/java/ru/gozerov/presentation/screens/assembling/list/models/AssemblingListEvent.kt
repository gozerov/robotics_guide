package ru.gozerov.presentation.screens.assembling.list.models

sealed interface AssemblingListEvent {

    object None: AssemblingListEvent

    class LoadedCategories(
        val categories: List<String>
    ): AssemblingListEvent

}