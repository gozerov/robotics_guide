package ru.gozerov.presentation.screens.profile.unlogged

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.gozerov.domain.use_cases.SaveLabTokenUseCase
import ru.gozerov.presentation.screens.profile.unlogged.models.AuthorizationEffect
import ru.gozerov.presentation.screens.profile.unlogged.models.AuthorizationIntent
import ru.gozerov.presentation.screens.profile.unlogged.models.AuthorizationViewState
import ru.gozerov.presentation.utils.runCatchingNonCancellation
import javax.inject.Inject

@HiltViewModel
class AuthorizationViewModel @Inject constructor(
    private val saveLabTokenUseCase: SaveLabTokenUseCase
) : ViewModel() {

    private val _viewState = MutableStateFlow<AuthorizationViewState>(AuthorizationViewState.Empty)
    val viewState: StateFlow<AuthorizationViewState>
        get() = _viewState.asStateFlow()

    private val _effect = MutableStateFlow<AuthorizationEffect>(AuthorizationEffect.None)
    val effect: StateFlow<AuthorizationEffect>
        get() = _effect.asStateFlow()

    fun handleIntent(intent: AuthorizationIntent) {
        viewModelScope.launch {
            when (intent) {
                is AuthorizationIntent.Login -> {
                    _viewState.emit(AuthorizationViewState.Loading)
                }

                is AuthorizationIntent.SaveLabToken -> {
                    runCatchingNonCancellation {
                        saveLabTokenUseCase.invoke(intent.token, intent.id)
                    }
                        .map {
                            _effect.emit(AuthorizationEffect.NavigateToProfile)
                        }
                        .onFailure {
                            _effect.emit(AuthorizationEffect.Error)
                        }
                }
            }
        }
    }


}