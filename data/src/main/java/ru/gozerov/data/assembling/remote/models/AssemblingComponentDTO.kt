package ru.gozerov.data.assembling.remote.models

import com.squareup.moshi.Json

data class AssemblingComponentDTO(
    @Json(name = "component_id") val componentId: Int,
    val name: String,
    val amount: Int,
    @Json(name = "link_to_photo") val photoUrl: String?,
    @Json(name = "link_to_sound") val linkToSound: String?
)
