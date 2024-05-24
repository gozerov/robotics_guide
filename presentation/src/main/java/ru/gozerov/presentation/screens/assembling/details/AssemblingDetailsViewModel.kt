package ru.gozerov.presentation.screens.assembling.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.gozerov.domain.use_cases.GetAssemblingDetailsUseCase
import ru.gozerov.presentation.screens.assembling.details.models.AssemblingDetailsIntent
import ru.gozerov.presentation.screens.assembling.details.models.AssemblingDetailsViewState
import ru.gozerov.presentation.utils.runCatchingNonCancellation
import javax.inject.Inject

@HiltViewModel
class AssemblingDetailsViewModel @Inject constructor(
    private val getAssemblingDetailsUseCase: GetAssemblingDetailsUseCase
) : ViewModel() {

    private val _viewState =
        MutableStateFlow<AssemblingDetailsViewState>(AssemblingDetailsViewState.Empty)
    val viewState: StateFlow<AssemblingDetailsViewState>
        get() = _viewState.asStateFlow()

    fun handleIntent(intent: AssemblingDetailsIntent) {
        viewModelScope.launch {
            when (intent) {
                is AssemblingDetailsIntent.LoadAssembling -> {
                    runCatchingNonCancellation {
                        getAssemblingDetailsUseCase(intent.id)
                    }
                        .map { flow ->
                            flow.collect { assembling ->
                                _viewState.emit(
                                    AssemblingDetailsViewState.LoadedAssembling(assembling)
                                )
                            }
                        }
                        .onFailure {
                            _viewState.emit(AssemblingDetailsViewState.Error())
                        }
                }
            }
        }
    }

}