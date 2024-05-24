package ru.gozerov.presentation.screens.assembling.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.gozerov.domain.use_cases.GetAssemblingListUseCase
import ru.gozerov.domain.use_cases.GetCategoriesUseCase
import ru.gozerov.domain.use_cases.SearchAssemblingUseCase
import ru.gozerov.presentation.screens.assembling.list.models.AssemblingListEffect
import ru.gozerov.presentation.screens.assembling.list.models.AssemblingListIntent
import ru.gozerov.presentation.screens.assembling.list.models.AssemblingListViewState
import ru.gozerov.presentation.utils.runCatchingNonCancellation
import javax.inject.Inject

@HiltViewModel
class AssemblingListViewModel @Inject constructor(
    private val getAssemblingListUseCase: GetAssemblingListUseCase,
    private val searchAssemblingUseCase: SearchAssemblingUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase
) : ViewModel() {

    private val _viewState =
        MutableStateFlow<AssemblingListViewState>(AssemblingListViewState.Empty)
    val viewState: StateFlow<AssemblingListViewState>
        get() = _viewState.asStateFlow()

    private val _effect = MutableStateFlow<AssemblingListEffect>(AssemblingListEffect.None)
    val effect: StateFlow<AssemblingListEffect>
        get() = _effect.asStateFlow()

    fun handleIntent(intent: AssemblingListIntent) {
        viewModelScope.launch {
            when (intent) {
                is AssemblingListIntent.LoadAssemblings -> {
                    runCatchingNonCancellation {
                        getAssemblingListUseCase.invoke()
                    }
                        .map {
                            _viewState.emit(
                                AssemblingListViewState.LoadedAssemblings(it.first, it.second)
                            )
                        }
                        .onFailure {
                            if (it.message == HTTP_401)
                                _effect.emit(AssemblingListEffect.NavigateToProfile)
                            else
                                _viewState.emit(AssemblingListViewState.Error)
                        }
                }

                is AssemblingListIntent.SearchAssembling -> {
                    runCatchingNonCancellation {
                        searchAssemblingUseCase(intent.query, intent.category)
                    }
                        .map {
                            _viewState.emit(AssemblingListViewState.SearchedAssemblings(it))
                        }
                        .onFailure {
                            _viewState.emit(AssemblingListViewState.Error)
                        }
                }

                is AssemblingListIntent.LoadCategories -> {
                    runCatchingNonCancellation {
                        getCategoriesUseCase()
                    }
                        .map {
                            _effect.emit(AssemblingListEffect.LoadedCategories(it))
                        }
                        .onFailure {
                            _viewState.emit(AssemblingListViewState.Error)
                        }
                }
            }
        }
    }

    companion object {

        private const val HTTP_401 = "HTTP 401 "

    }

}