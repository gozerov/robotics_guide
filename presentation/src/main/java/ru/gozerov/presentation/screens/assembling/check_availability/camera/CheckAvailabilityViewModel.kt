package ru.gozerov.presentation.screens.assembling.check_availability.camera

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.gozerov.domain.models.assembling.Component
import ru.gozerov.domain.use_cases.GetComponentByIdUseCase
import ru.gozerov.presentation.screens.assembling.check_availability.camera.models.CheckAvailabilityEffect
import ru.gozerov.presentation.screens.assembling.check_availability.camera.models.CheckAvailabilityIntent
import ru.gozerov.presentation.utils.runCatchingNonCancellation
import javax.inject.Inject

@HiltViewModel
class CheckAvailabilityViewModel @Inject constructor(
    private val getComponentByIdUseCase: GetComponentByIdUseCase
) : ViewModel() {

    private val _effect =
        MutableStateFlow<CheckAvailabilityEffect>(CheckAvailabilityEffect.None)

    val effect: SharedFlow<CheckAvailabilityEffect>
        get() = _effect.asStateFlow()

    private val components = mutableListOf<Component>()

    var isCameraActive = true
        private set

    fun handleIntent(intent: CheckAvailabilityIntent) {
        viewModelScope.launch {
            when (intent) {
                is CheckAvailabilityIntent.AddComponent -> {
                    runCatchingNonCancellation {
                        getComponentByIdUseCase(intent.id)
                    }
                        .map { component ->
                            components.add(component)
                            _effect.emit(CheckAvailabilityEffect.ShowDialog(components.toList()))
                        }
                        .onFailure {
                            _effect.emit(CheckAvailabilityEffect.Error(intent.id.toString()))
                        }
                }

                is CheckAvailabilityIntent.SetCameraActive -> {
                    isCameraActive = intent.isActive
                }

                is CheckAvailabilityIntent.ShowError -> {
                    isCameraActive = true
                    _effect.emit(CheckAvailabilityEffect.Error(intent.message))
                }
            }
        }
    }

}