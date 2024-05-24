package ru.gozerov.data.assembling.remote.models

import com.squareup.moshi.Json

data class ContainerDTO(
    val number: String,
    val room: String,
    val amount: Int,
    @Json(name = "component_id") val componentId: Int
)
