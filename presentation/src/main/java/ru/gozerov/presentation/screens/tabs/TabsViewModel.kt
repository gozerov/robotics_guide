package ru.gozerov.presentation.screens.tabs

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import ru.gozerov.domain.use_cases.CheckIsAuthorizedUseCase
import ru.gozerov.presentation.screens.tabs.models.TabsEffect
import ru.gozerov.presentation.screens.tabs.models.TabsIntent
import ru.gozerov.presentation.utils.runCatchingNonCancellation
import javax.inject.Inject

@HiltViewModel
class TabsViewModel @Inject constructor(
    private val checkIsAuthorizedUseCase: CheckIsAuthorizedUseCase
) : ViewModel() {

    private val _effect = MutableSharedFlow<TabsEffect>(1, 0, BufferOverflow.DROP_OLDEST)
    val effect: SharedFlow<TabsEffect>
        get() = _effect.asSharedFlow()

    fun handleIntent(intent: TabsIntent) {
        viewModelScope.launch {
            when (intent) {
                is TabsIntent.CheckIsAuthorized -> {
                    runCatchingNonCancellation {
                        checkIsAuthorizedUseCase.invoke()
                    }
                        .map { isAuthorized ->
                            _effect.emit(TabsEffect.CheckResult(isAuthorized))
                        }
                }
            }
        }
    }

}