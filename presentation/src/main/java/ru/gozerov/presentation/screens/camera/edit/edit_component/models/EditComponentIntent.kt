package ru.gozerov.presentation.screens.camera.edit.edit_component.models

import android.net.Uri

sealed interface EditComponentIntent {

    class SaveChanges(
        val name: String,
        val imageUri: Uri?
    ) : EditComponentIntent

}