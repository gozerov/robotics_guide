package ru.gozerov.presentation.screens.assembling.assembly_process

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.gozerov.domain.use_cases.GetCurrentStepUseCase
import ru.gozerov.domain.use_cases.GetSynthesizedSpeechUseCase
import ru.gozerov.domain.use_cases.MoveOnNextStepUseCase
import ru.gozerov.presentation.screens.assembling.assembly_process.models.AssemblyProcessEffect
import ru.gozerov.presentation.screens.assembling.assembly_process.models.AssemblyProcessIntent
import ru.gozerov.presentation.screens.assembling.assembly_process.models.AssemblyProcessViewState
import ru.gozerov.presentation.utils.runCatchingNonCancellation
import javax.inject.Inject

@HiltViewModel
class AssemblyProcessViewModel @Inject constructor(
    private val getCurrentStepUseCase: GetCurrentStepUseCase,
    private val getSynthesizedSpeechUseCase: GetSynthesizedSpeechUseCase,
    private val moveOnNextStepUseCase: MoveOnNextStepUseCase
) : ViewModel() {

    private val _viewState =
        MutableStateFlow<AssemblyProcessViewState>(AssemblyProcessViewState.Empty)
    val viewState: StateFlow<AssemblyProcessViewState>
        get() = _viewState.asStateFlow()

    private val _effect =
        MutableSharedFlow<AssemblyProcessEffect>(1, 0, BufferOverflow.DROP_OLDEST)

    val effect: SharedFlow<AssemblyProcessEffect>
        get() = _effect.asSharedFlow()

    fun handleIntent(intent: AssemblyProcessIntent) {
        viewModelScope.launch {
            when (intent) {
                is AssemblyProcessIntent.ShowError -> {
                    _viewState.emit(AssemblyProcessViewState.Error())
                }

                is AssemblyProcessIntent.LoadStep -> {
                    runCatchingNonCancellation {
                        getCurrentStepUseCase(intent.assemblingId)
                    }
                        .map {
                            _viewState.emit(AssemblyProcessViewState.LoadedStep(it))
                        }
                        .onFailure {
                            _viewState.emit(AssemblyProcessViewState.Error())
                        }
                }

                is AssemblyProcessIntent.LoadSpeech -> {
                    runCatchingNonCancellation {
                        getSynthesizedSpeechUseCase.invoke(intent.componentId, intent.name, intent.fileUrl)
                    }
                        .map {
                            _effect.emit(AssemblyProcessEffect.LoadedSpeech())
                        }
                        .onFailure {
                            _viewState.emit(AssemblyProcessViewState.Error())
                        }
                }

                is AssemblyProcessIntent.MoveOnNext -> {
                    runCatchingNonCancellation {
                        moveOnNextStepUseCase(intent.isBack)
                    }
                        .map {
                            _effect.emit(AssemblyProcessEffect.Navigate(intent.isBack))
                        }
                        .onFailure {
                            _viewState.emit(AssemblyProcessViewState.Error())
                        }
                }

                is AssemblyProcessIntent.SetEnabled -> {
                    when (intent.enabled) {
                        true -> _effect.emit(AssemblyProcessEffect.RecordOn())
                        false -> _effect.emit(AssemblyProcessEffect.RecordOff())
                    }
                }

                is AssemblyProcessIntent.SetPause -> {
                    when (intent.paused) {
                        true -> _effect.emit(AssemblyProcessEffect.RecordPaused())
                        false -> _effect.emit(AssemblyProcessEffect.RecordContinued())
                    }
                }

                is AssemblyProcessIntent.RepeatRecord -> {
                    _effect.emit(AssemblyProcessEffect.RepeatRecord())
                }
            }
        }
    }

}