package ru.gozerov.presentation.screens.assembling.list.models

sealed interface AssemblingListIntent {

    object CheckAuthorization : AssemblingListIntent

    object LoadAssemblings : AssemblingListIntent

    object LoadCategories : AssemblingListIntent

    class SearchAssembling(
        val query: String,
        val category: String
    ) : AssemblingListIntent

}