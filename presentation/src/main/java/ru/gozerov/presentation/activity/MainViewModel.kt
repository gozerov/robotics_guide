package ru.gozerov.presentation.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.gozerov.domain.use_cases.UpdateAuthorizationUseCase
import ru.gozerov.presentation.activity.models.MainActivityEffect
import ru.gozerov.presentation.activity.models.MainActivityIntent
import ru.gozerov.presentation.utils.runCatchingNonCancellation
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val updateAuthorizationUseCase: UpdateAuthorizationUseCase
) : ViewModel() {

    private val _effect = MutableStateFlow<MainActivityEffect>(MainActivityEffect.None)
    val effect: StateFlow<MainActivityEffect>
        get() = _effect.asStateFlow()

    init {
        handleIntent(MainActivityIntent.CheckAuthorization)
    }

    fun handleIntent(intent: MainActivityIntent) {
        viewModelScope.launch {
            when (intent) {
                is MainActivityIntent.CheckAuthorization -> {
                    runCatchingNonCancellation {
                        updateAuthorizationUseCase.invoke()
                    }
                        .map { isAuthorized ->
                            _effect.emit(MainActivityEffect.CheckedAuth(isAuthorized))
                        }
                        .onFailure {
                            _effect.emit(MainActivityEffect.Error)
                        }
                }
            }
        }
    }

}