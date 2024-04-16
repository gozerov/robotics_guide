package ru.gozerov.domain.use_cases

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.gozerov.domain.models.assembling.FilterCategory
import ru.gozerov.domain.repositories.AssemblingRepository
import javax.inject.Inject

class SearchAssemblingUseCase @Inject constructor(
    private val assemblingRepository: AssemblingRepository
) {

    suspend operator fun invoke(query: String, category: String) =
        withContext(Dispatchers.IO) {
            val filterCategory = when (category) {
                "Новые" -> FilterCategory.NEW
                else -> FilterCategory.POPULAR
            }
            return@withContext assemblingRepository.searchAssembling(query, filterCategory)
        }

}