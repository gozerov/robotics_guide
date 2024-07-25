package ru.gozerov.presentation.screens.profile.logged

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.gozerov.domain.use_cases.GetOwnProfileUseCase
import ru.gozerov.domain.use_cases.LogoutUseCase
import ru.gozerov.presentation.screens.profile.logged.models.ProfileIntent
import ru.gozerov.presentation.screens.profile.logged.models.ProfileViewState
import ru.gozerov.presentation.utils.runCatchingNonCancellation
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getOwnProfileUseCase: GetOwnProfileUseCase,
    private val logoutUseCase: LogoutUseCase
) : ViewModel() {

    private val _viewState = MutableStateFlow<ProfileViewState>(ProfileViewState.Empty)
    val viewState: StateFlow<ProfileViewState>
        get() = _viewState.asStateFlow()

    fun handleIntent(intent: ProfileIntent) {
        viewModelScope.launch {
            when (intent) {
                is ProfileIntent.LoadProfile -> {
                    runCatchingNonCancellation {
                        getOwnProfileUseCase.invoke()
                    }
                        .map { profile ->
                            _viewState.emit(ProfileViewState.LoadedProfile(userProfile = profile))
                        }
                        .onFailure {
                            _viewState.emit(ProfileViewState.Error)
                        }
                }
                is ProfileIntent.Logout -> {
                    logoutUseCase.invoke()
                }
            }
        }
    }

}