package ru.gozerov.domain.use_cases

import ru.gozerov.domain.models.assembling.Container
import ru.gozerov.domain.repositories.AssemblingRepository
import javax.inject.Inject

class UpdateContainerUseCase @Inject constructor(
    private val assemblingRepository: AssemblingRepository
) {

    suspend operator fun invoke(number: String, container: Container) {
        assemblingRepository.updateContainer(number, container)
    }

}