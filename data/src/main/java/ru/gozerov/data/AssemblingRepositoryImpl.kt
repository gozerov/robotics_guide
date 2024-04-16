package ru.gozerov.data

import ru.gozerov.domain.models.assembling.Assembling
import ru.gozerov.domain.models.assembling.Component
import ru.gozerov.domain.models.assembling.Container
import ru.gozerov.domain.models.assembling.FilterCategory
import ru.gozerov.domain.models.assembling.SimpleAssembling
import ru.gozerov.domain.repositories.AssemblingRepository
import javax.inject.Inject

class AssemblingRepositoryImpl @Inject constructor() : AssemblingRepository {

    private val categories = listOf("Популярные", "Новые")

    private val assemblingStub = listOf(
        Assembling(
            1,
            "Робот-панда",
            listOf(
                Container(
                    0,
                    Component(0, "Винт М3-20", "https://i.imgur.com/rEda6eO.png", "A-415"),
                    4
                ),
                Container(
                    1,
                    Component(1, "Шестигранная гайка", null, "A-415"),
                    6
                ),
                Container(
                    2,
                    Component(2, "Болт", null, "A-415"),
                    6
                )
            ), "", "",
            null, 1
        ),
        Assembling(
            2, "Лапка робота собаки", listOf(
                Container(
                    0,
                    Component(0, "Винт М3-20", "https://i.imgur.com/rEda6eO.png", "A-415"),
                    4
                ),
                Container(
                    1,
                    Component(1, "Шестигранная гайка", null, "A-415"),
                    6
                ),
                Container(
                    2,
                    Component(2, "Болт", null, "A-415"),
                    6
                )
            ), "", "",
            null, 1
        ),
        Assembling(
            3, "Робот-панда", listOf(
                Container(
                    0,
                    Component(0, "Винт М3-20", "https://i.imgur.com/rEda6eO.png", "A-415"),
                    4
                ),
                Container(
                    1,
                    Component(1, "Шестигранная gsfagfgggggggggggggggggggggggggggggggggggggggggggggggggggggggggggгайка", null, "A-415"),
                    6
                ),
                Container(
                    2,
                    Component(2, "Болт", null, "A-415"),
                    6
                )
            ), "", "",
            null, 1
        )
    )

    override suspend fun getSimpleAssemblingList(): Pair<List<SimpleAssembling>, List<SimpleAssembling>> {
        val assemblings = assemblingStub.map { it.toSimpleAssembling() }
        return assemblings to assemblings
    }

    override suspend fun getAssemblingById(id: Int): Assembling {
        return assemblingStub.first { it.id == id }
    }

    override suspend fun searchAssembling(
        query: String,
        category: FilterCategory
    ): List<SimpleAssembling> {
        return if (query.isBlank())
            assemblingStub.map { it.toSimpleAssembling() }
        else
            assemblingStub.filter { it.name.lowercase().contains(query.lowercase()) }
                .map { it.toSimpleAssembling() }
    }

    override suspend fun getCategories(): List<String> {
        return categories
    }

}