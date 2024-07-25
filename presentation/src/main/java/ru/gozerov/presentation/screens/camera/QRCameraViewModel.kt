package ru.gozerov.presentation.screens.camera

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.gozerov.domain.use_cases.GetComponentByIdUseCase
import ru.gozerov.domain.use_cases.GetContainerByIdUseCase
import ru.gozerov.presentation.screens.camera.models.QRCameraEffect
import ru.gozerov.presentation.screens.camera.models.QRCameraIntent
import ru.gozerov.presentation.utils.runCatchingNonCancellation
import javax.inject.Inject

@HiltViewModel
class QRCameraViewModel @Inject constructor(
    private val getComponentByIdUseCase: GetComponentByIdUseCase,
    private val getContainerByIdUseCase: GetContainerByIdUseCase
) : ViewModel() {

    private val _effect =
        MutableStateFlow<QRCameraEffect>(QRCameraEffect.None)

    val effect: StateFlow<QRCameraEffect>
        get() = _effect.asStateFlow()

    var isCameraActive = true
        private set

    var lastScanned: String = ""

    fun handleIntent(intent: QRCameraIntent) {
        viewModelScope.launch {
            when (intent) {
                is QRCameraIntent.ObtainQR -> {
                    try {
                        val regex =
                            "https://assemble\\.rtuitlab\\.dev/(container|component)/(\\w+)".toRegex()
                        lastScanned = intent.text
                        val matchResult = regex.find(intent.text)
                        val nullableId = matchResult?.groupValues?.get(2)
                        nullableId?.let { id ->
                            if (intent.text.contains("container")) {
                                runCatchingNonCancellation {
                                    getContainerByIdUseCase.invoke(id)
                                }
                                    .onSuccess { container ->
                                        _effect.emit(QRCameraEffect.ShowContainer(container))
                                    }
                                    .onFailure {
                                        _effect.emit(QRCameraEffect.Error(id))
                                    }
                            } else if (intent.text.contains("component") && id.toIntOrNull() != null) {
                                runCatchingNonCancellation {
                                    getComponentByIdUseCase.invoke(id.toInt())
                                }
                                    .onSuccess { component ->
                                        _effect.emit(QRCameraEffect.ShowComponent(component))
                                    }
                                    .onFailure {
                                        _effect.emit(QRCameraEffect.Error(id))
                                    }
                            } else return@let
                        }
                    } catch (e: Exception) {
                        _effect.emit(QRCameraEffect.Error("error"))
                    }
                }

                is QRCameraIntent.SetCameraActive -> {
                    isCameraActive = intent.isActive
                }

                is QRCameraIntent.ShowError -> {
                    isCameraActive = true
                    _effect.emit(QRCameraEffect.Error(intent.message))
                }

                is QRCameraIntent.Navigate -> {
                    _effect.emit(QRCameraEffect.None)
                }
            }
        }
    }

}