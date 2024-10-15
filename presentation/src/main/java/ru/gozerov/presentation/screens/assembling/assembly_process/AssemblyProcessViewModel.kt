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
import ru.gozerov.domain.use_cases.MoveOnNextStepUseCase
import ru.gozerov.presentation.screens.assembling.assembly_process.models.AssemblyProcessEffect
import ru.gozerov.presentation.screens.assembling.assembly_process.models.AssemblyProcessIntent
import ru.gozerov.presentation.screens.assembling.assembly_process.models.AssemblyProcessViewState
import ru.gozerov.presentation.utils.runCatchingNonCancellation
import javax.inject.Inject

@HiltViewModel
class AssemblyProcessViewModel @Inject constructor(
    private val getCurrentStepUseCase: GetCurrentStepUseCase,
    private val moveOnNextStepUseCase: MoveOnNextStepUseCase
) : ViewModel() {

    private val _viewState =
        MutableStateFlow<AssemblyProcessViewState>(AssemblyProcessViewState.Empty)
    val viewState: StateFlow<AssemblyProcessViewState>
        get() = _viewState.asStateFlow()

    private val _effects =
        MutableSharedFlow<AssemblyProcessEffect>(1, 0, BufferOverflow.DROP_OLDEST)

    val effects: SharedFlow<AssemblyProcessEffect>
        get() = _effects.asSharedFlow()

    fun handleIntent(intent: AssemblyProcessIntent) {
        viewModelScope.launch {
            when (intent) {
                is AssemblyProcessIntent.Empty -> {
                    _viewState.emit(AssemblyProcessViewState.Empty)
                    _effects.emit(AssemblyProcessEffect.None)
                }

                is AssemblyProcessIntent.LoadStep -> {
                    runCatchingNonCancellation {
                        getCurrentStepUseCase(intent.assemblingId)
                    }
                        .onSuccess { step ->
                            _effects.emit(AssemblyProcessEffect.RecordOn(step.container.name))
                            _viewState.emit(AssemblyProcessViewState.LoadedStep(step))
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
                            _effects.emit(AssemblyProcessEffect.Navigate(intent.isBack))
                        }
                        .onFailure {
                            _viewState.emit(AssemblyProcessViewState.Error())
                        }
                }

                is AssemblyProcessIntent.SetEnabled -> {
                    when (intent.enabled) {
                        true -> {
                            val componentName =
                                (_viewState.value as? AssemblyProcessViewState.LoadedStep)?.step?.container?.name
                            _effects.emit(AssemblyProcessEffect.RecordOn(componentName))
                        }

                        false -> _effects.emit(AssemblyProcessEffect.RecordOff())
                    }
                }

                is AssemblyProcessIntent.SetPause -> {
                    when (intent.paused) {
                        true -> _effects.emit(AssemblyProcessEffect.RecordPaused())
                        false -> _effects.emit(AssemblyProcessEffect.RecordContinued())
                    }
                }

                is AssemblyProcessIntent.RepeatRecord -> {
                    _effects.emit(AssemblyProcessEffect.RepeatRecord())
                }
            }
        }
    }

}