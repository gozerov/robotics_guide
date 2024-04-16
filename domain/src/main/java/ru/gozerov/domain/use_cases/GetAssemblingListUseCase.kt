package ru.gozerov.domain.use_cases

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.gozerov.domain.repositories.AssemblingRepository
import javax.inject.Inject

class GetAssemblingListUseCase @Inject constructor(
    private val assemblingRepository: AssemblingRepository
) {

    suspend operator fun invoke() = withContext(Dispatchers.IO) {
        return@withContext assemblingRepository.getSimpleAssemblingList()
    }

}