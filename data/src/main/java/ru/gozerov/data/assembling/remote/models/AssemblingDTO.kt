package ru.gozerov.data.assembling.remote.models

import com.squareup.moshi.Json

data class AssemblingDTO(
    @Json(name = "assemble_id") val id: Int,
    val name: String,
    val instruction: String,
    @Json(name = "amount_ready") val readyAmount: Int,
    @Json(name = "link_to_project") val linkToProject: String,
    @Json(name = "link_to_sound") val linkToSound: String?,
    @Json(name = "user_id") val userId: Int,
    val components: List<AssemblingComponentDTO>
)
