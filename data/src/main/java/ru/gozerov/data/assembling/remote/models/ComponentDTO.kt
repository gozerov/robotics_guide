package ru.gozerov.data.assembling.remote.models

data class ComponentDTO(
    val id: Int,
    val name: String,
    val linkToImage: String?,
    val containers: List<SimpleContainerDTO>?
)