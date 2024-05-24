package ru.gozerov.presentation.activity.models

sealed interface MainActivityEffect {

    object None : MainActivityEffect

    class CheckedAuth(
        val isAuthorized: Boolean
    ) : MainActivityEffect

    object Error : MainActivityEffect

}