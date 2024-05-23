package ru.gozerov.domain.use_cases

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.gozerov.domain.repositories.LoginRepository
import javax.inject.Inject

class SaveLabTokenUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) {

    suspend operator fun invoke(bearer: String, id: String) = withContext(Dispatchers.IO) {
        loginRepository.saveLabTokenAndId(bearer, id)
    }

}