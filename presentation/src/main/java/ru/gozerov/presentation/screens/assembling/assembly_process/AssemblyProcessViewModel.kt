package ru.gozerov.presentation.screens.assembling.assembly_process

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.gozerov.domain.use_cases.GetCurrentStepUseCase
import ru.gozerov.domain.use_cases.MoveOnNextStepUseCase
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

    fun handleIntent(intent: AssemblyProcessIntent) {
        viewModelScope.launch {
            when (intent) {
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

                is AssemblyProcessIntent.MoveOnNext -> {
                    runCatchingNonCancellation {
                        moveOnNextStepUseCase(intent.isBack)
                    }
                        .onFailure {
                            _viewState.emit(AssemblyProcessViewState.Error())
                        }
                }
            }
        }
    }

}