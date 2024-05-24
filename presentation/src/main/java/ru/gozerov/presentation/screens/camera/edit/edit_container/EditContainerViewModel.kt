package ru.gozerov.presentation.screens.camera.edit.edit_container

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.gozerov.domain.use_cases.UpdateContainerUseCase
import ru.gozerov.presentation.screens.camera.edit.edit_container.models.EditContainerEffect
import ru.gozerov.presentation.screens.camera.edit.edit_container.models.EditContainerIntent
import ru.gozerov.presentation.utils.runCatchingNonCancellation
import javax.inject.Inject

@HiltViewModel
class EditContainerViewModel @Inject constructor(
    private val updateContainerUseCase: UpdateContainerUseCase
) : ViewModel() {

    private val _effect = MutableStateFlow<EditContainerEffect>(EditContainerEffect.None)
    val effect: StateFlow<EditContainerEffect>
        get() = _effect.asStateFlow()

    fun handleIntent(intent: EditContainerIntent) {
        viewModelScope.launch {
            when (intent) {
                is EditContainerIntent.SaveChanges -> {
                    runCatchingNonCancellation {
                        updateContainerUseCase.invoke(intent.container)
                    }
                        .map {
                            _effect.emit(EditContainerEffect.ShowDialog)
                        }
                        .onFailure {
                            _effect.emit(EditContainerEffect.Error)
                        }
                }

                is EditContainerIntent.ShowError -> {
                    _effect.emit(EditContainerEffect.Error)
                }
            }
        }
    }

}