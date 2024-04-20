package ru.gozerov.domain.use_cases

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.gozerov.domain.repositories.AssemblingRepository
import javax.inject.Inject

class MoveOnNextStepUseCase @Inject constructor(
    private val assemblingRepository: AssemblingRepository
) {

    suspend operator fun invoke(back: Boolean) = withContext(Dispatchers.IO) {
        assemblingRepository.nextStep(back)
    }

}