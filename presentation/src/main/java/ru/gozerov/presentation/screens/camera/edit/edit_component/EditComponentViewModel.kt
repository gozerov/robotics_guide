package ru.gozerov.presentation.screens.camera.edit.edit_component

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.gozerov.domain.use_cases.UpdateComponentUseCase
import ru.gozerov.presentation.screens.camera.edit.edit_component.models.EditComponentEffect
import ru.gozerov.presentation.screens.camera.edit.edit_component.models.EditComponentIntent
import ru.gozerov.presentation.utils.runCatchingNonCancellation
import javax.inject.Inject

@HiltViewModel
class EditComponentViewModel @Inject constructor(
    private val updateComponentUseCase: UpdateComponentUseCase
) : ViewModel() {

    private val _effect = MutableStateFlow<EditComponentEffect>(EditComponentEffect.None)
    val effect: StateFlow<EditComponentEffect>
        get() = _effect.asStateFlow()

    fun handleIntent(intent: EditComponentIntent) {
        viewModelScope.launch {
            when (intent) {
                is EditComponentIntent.SaveChanges -> {
                    runCatchingNonCancellation {
                        updateComponentUseCase.invoke(intent.id, intent.name, intent.imageUri)
                    }
                        .map {
                            _effect.emit(EditComponentEffect.ShowDialog)
                        }
                        .onFailure {
                            _effect.emit(EditComponentEffect.Error)
                        }
                }
            }
        }
    }

}