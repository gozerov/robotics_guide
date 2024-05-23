package ru.gozerov.presentation.screens.profile.unlogged.models

sealed interface AuthorizationEffect {

    object None: AuthorizationEffect

    object NavigateToProfile: AuthorizationEffect

    object Error: AuthorizationEffect

}