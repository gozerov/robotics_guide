package ru.gozerov.presentation.screens.profile.logged.models

import ru.gozerov.domain.models.login.UserProfile

sealed interface ProfileViewState {

    object Empty : ProfileViewState

    class LoadedProfile(
        val userProfile: UserProfile
    ) : ProfileViewState

    object Error : ProfileViewState

}