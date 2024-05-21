package ru.gozerov.domain.use_cases

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.gozerov.domain.repositories.AssemblingRepository
import javax.inject.Inject

class GetContainerByIdUseCase @Inject constructor(
    private val assemblingRepository: AssemblingRepository
) {

    suspend operator fun invoke(id: Int) = withContext(Dispatchers.IO) {
        return@withContext assemblingRepository.getContainerById(id)
    }

}