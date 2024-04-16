package ru.gozerov.presentation.screens.assembling.details.models

sealed interface AssemblingDetailsIntent {

    class LoadAssembling(
        val id: Int
    ): AssemblingDetailsIntent

}