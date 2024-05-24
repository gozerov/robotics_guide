package ru.gozerov.presentation.activity.models

sealed interface MainActivityIntent {

    object CheckAuthorization : MainActivityIntent

}