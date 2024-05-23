package ru.gozerov.presentation.screens.tabs.models

sealed interface TabsEffect {

    class CheckResult(
        val isAuthorized: Boolean
    ): TabsEffect

}