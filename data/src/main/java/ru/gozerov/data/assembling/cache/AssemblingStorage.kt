package ru.gozerov.data.assembling.cache

import ru.gozerov.data.assembling.remote.models.AssemblingDTO
import ru.gozerov.domain.models.assembling.Assembling

interface AssemblingStorage {

    suspend fun insertAssembling(assembling: AssemblingDTO)

    suspend fun getAssemblingById(id: Int): Assembling?

}