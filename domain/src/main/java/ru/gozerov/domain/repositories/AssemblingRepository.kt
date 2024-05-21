package ru.gozerov.domain.repositories

import ru.gozerov.domain.models.assembling.Assembling
import ru.gozerov.domain.models.assembling.AssemblyStep
import ru.gozerov.domain.models.assembling.Component
import ru.gozerov.domain.models.assembling.Container
import ru.gozerov.domain.models.assembling.FilterCategory
import ru.gozerov.domain.models.assembling.SimpleAssembling

interface AssemblingRepository {

    suspend fun getSimpleAssemblingList(): Pair<List<SimpleAssembling>, List<SimpleAssembling>>

    suspend fun getAssemblingById(id: Int): Assembling

    suspend fun searchAssembling(query: String, category: FilterCategory): List<SimpleAssembling>

    suspend fun getCategories(): List<String>

    suspend fun getCurrentStep(assemblingId: Int): AssemblyStep

    suspend fun nextStep(back: Boolean = false)

    suspend fun getComponentById(id: Int): Component

    suspend fun getContainerById(id: Int): Container

}