package ru.gozerov.presentation.screens.tabs.models

sealed interface TabsIntent {

    object CheckIsAuthorized: TabsIntent

}