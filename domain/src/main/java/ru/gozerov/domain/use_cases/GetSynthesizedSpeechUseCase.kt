package ru.gozerov.domain.use_cases

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.gozerov.domain.repositories.AssemblingRepository
import javax.inject.Inject

class GetSynthesizedSpeechUseCase @Inject constructor(
    private val assemblingRepository: AssemblingRepository
) {

    suspend operator fun invoke(componentId: Int, name: String, fileUrl: String) =
        withContext(Dispatchers.IO) {
            assemblingRepository.loadSpeech(componentId, name, fileUrl)
        }

}