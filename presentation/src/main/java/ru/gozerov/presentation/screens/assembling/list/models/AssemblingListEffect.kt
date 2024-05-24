package ru.gozerov.presentation.screens.assembling.list.models

sealed interface AssemblingListEffect {

    object None: AssemblingListEffect

    class LoadedCategories(
        val categories: List<String>
    ): AssemblingListEffect

    object NavigateToProfile: AssemblingListEffect

}