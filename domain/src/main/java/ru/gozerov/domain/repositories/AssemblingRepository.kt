package ru.gozerov.domain.repositories

import android.net.Uri
import kotlinx.coroutines.flow.Flow
import ru.gozerov.domain.models.assembling.Assembling
import ru.gozerov.domain.models.assembling.AssemblyStep
import ru.gozerov.domain.models.assembling.Component
import ru.gozerov.domain.models.assembling.Container
import ru.gozerov.domain.models.assembling.FilterCategory
import ru.gozerov.domain.models.assembling.SimpleAssembling
import java.io.File

interface AssemblingRepository {

    suspend fun getSimpleAssemblingList(): Pair<List<SimpleAssembling>, List<SimpleAssembling>>

    suspend fun getAssemblingById(id: Int): Flow<Assembling>

    suspend fun searchAssembling(query: String, category: FilterCategory): List<SimpleAssembling>

    suspend fun getCategories(): List<String>

    suspend fun getCurrentStep(assemblingId: Int): AssemblyStep

    suspend fun nextStep(back: Boolean = false)

    suspend fun getComponentById(id: Int): Component

    suspend fun updateComponent(id: Int, name: String, uri: Uri?)

    suspend fun getContainerById(number: String): Container

    suspend fun updateContainer(container: Container)

    suspend fun loadSpeech(componentId: Int, name: String, speechUrl: String)

}