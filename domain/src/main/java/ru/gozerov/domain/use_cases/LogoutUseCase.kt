package ru.gozerov.domain.use_cases

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.gozerov.domain.repositories.LoginRepository
import javax.inject.Inject
import kotlin.math.log

class LogoutUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) {

    suspend operator fun invoke() = withContext(Dispatchers.IO) {
        loginRepository.logout()
    }

}