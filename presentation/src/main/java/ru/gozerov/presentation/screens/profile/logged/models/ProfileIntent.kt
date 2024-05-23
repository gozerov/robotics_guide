package ru.gozerov.presentation.screens.profile.logged.models

sealed interface ProfileIntent {

    object LoadProfile: ProfileIntent

    object Logout: ProfileIntent

}