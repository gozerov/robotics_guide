package ru.gozerov.presentation.screens.camera

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.gozerov.domain.use_cases.GetComponentByIdUseCase
import ru.gozerov.domain.use_cases.GetContainerByIdUseCase
import ru.gozerov.presentation.screens.camera.models.QRCameraEffect
import ru.gozerov.presentation.screens.camera.models.QRCameraIntent
import ru.gozerov.presentation.utils.runCatchingNonCancellation
import javax.inject.Inject

class QRCameraViewModel @Inject constructor(
    private val getComponentByIdUseCase: GetComponentByIdUseCase,
    private val getContainerByIdUseCase: GetContainerByIdUseCase
) : ViewModel() {

    private val _effect =
        MutableStateFlow<QRCameraEffect>(QRCameraEffect.None)

    val effect: SharedFlow<QRCameraEffect>
        get() = _effect.asStateFlow()

    var isCameraActive = true
        private set

    fun handleIntent(intent: QRCameraIntent) {
        viewModelScope.launch {
            when (intent) {
                is QRCameraIntent.ShowComponent -> {
                    runCatchingNonCancellation {
                        getComponentByIdUseCase(intent.id)
                    }
                        .map { component ->
                            _effect.emit(QRCameraEffect.ShowComponent(component))
                        }
                        .onFailure {
                            _effect.emit(QRCameraEffect.Error(intent.id.toString()))
                        }
                }

                is QRCameraIntent.ShowContainer -> {
                    runCatchingNonCancellation {
                        getContainerByIdUseCase(intent.id)
                    }
                        .map { container ->
                            _effect.emit(QRCameraEffect.ShowContainer(container))
                        }
                        .onFailure {
                            _effect.emit(QRCameraEffect.Error(intent.id.toString()))
                        }
                }

                is QRCameraIntent.SetCameraActive -> {
                    isCameraActive = intent.isActive
                }

                is QRCameraIntent.ShowError -> {
                    isCameraActive = true
                    _effect.emit(QRCameraEffect.Error(intent.message))
                }
            }
        }
    }

}