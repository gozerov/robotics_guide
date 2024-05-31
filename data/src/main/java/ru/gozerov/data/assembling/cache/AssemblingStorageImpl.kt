package ru.gozerov.data.assembling.cache

import ru.gozerov.data.assembling.cache.models.AssemblingComponentEntity
import ru.gozerov.data.assembling.remote.models.AssemblingDTO
import ru.gozerov.data.toAssemblingComponent
import ru.gozerov.data.toAssemblingEntity
import ru.gozerov.data.toComponentEntity
import ru.gozerov.domain.models.assembling.Assembling
import javax.inject.Inject

class AssemblingStorageImpl @Inject constructor(
    private val assemblingDao: AssemblingDao,
    private val componentDao: ComponentDao,
    private val assemblingComponentDao: AssemblingComponentDao
) : AssemblingStorage {

    override suspend fun insertAssembling(assembling: AssemblingDTO) {
        assemblingDao.insertAssembling(assembling.toAssemblingEntity())
        componentDao.insertComponents(assembling.components.map { it.toComponentEntity() })
        val components = assembling.components.map { component ->
            AssemblingComponentEntity(0, assembling.id, component.componentId)
        }
        assemblingComponentDao.clearByAssemblingId(assembling.id)
        assemblingComponentDao.insertComponents(components)
    }

    override suspend fun getAssemblingById(id: Int): Assembling? {
        val assemblingEntity = assemblingDao.getAssembling(id) ?: return null
        val componentIds = assemblingComponentDao.getComponentsId(id)
        val components = componentIds.map { componentDao.getComponent(it) }
        return Assembling(
            id = assemblingEntity.id,
            name = assemblingEntity.name,
            instruction = assemblingEntity.instruction,
            readyAmount = assemblingEntity.readyAmount,
            linkToProject = assemblingEntity.linkToProject,
            linkToSound = assemblingEntity.linkToSound,
            userId = assemblingEntity.userId,
            components = components.map { it.toAssemblingComponent() }
        )
    }

}