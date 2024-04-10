package ru.gozerov.domain.repositories

import ru.gozerov.domain.models.assembling.Assembling
import ru.gozerov.domain.models.assembling.FilterCategory
import ru.gozerov.domain.models.assembling.SimpleAssembling

interface AssemblingRepository {

    suspend fun getSimpleAssemblingList(): Result<List<SimpleAssembling>>

    suspend fun getAssemblingById(id: Int): Result<Assembling>

    suspend fun searchAssembling(query: String, category: FilterCategory): List<SimpleAssembling>

}