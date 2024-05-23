package ru.gozerov.presentation.screens.profile.unlogged.models

sealed interface AuthorizationIntent {

    object Login: AuthorizationIntent

    class SaveLabToken(
        val token: String,
        val id: String
    ): AuthorizationIntent

}