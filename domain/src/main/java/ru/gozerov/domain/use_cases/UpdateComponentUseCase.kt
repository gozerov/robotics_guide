package ru.gozerov.domain.use_cases

import android.net.Uri
import ru.gozerov.domain.repositories.AssemblingRepository
import javax.inject.Inject

class UpdateComponentUseCase @Inject constructor(
    private val assemblingRepository: AssemblingRepository
) {

    suspend operator fun invoke(id: Int, name: String, imageUri: Uri?) {
        assemblingRepository.updateComponent(id, name, imageUri)
    }

}