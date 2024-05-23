package ru.gozerov.presentation.screens.profile.unlogged.models

sealed interface AuthorizationViewState {

    object Empty: AuthorizationViewState

    object Loading: AuthorizationViewState

}