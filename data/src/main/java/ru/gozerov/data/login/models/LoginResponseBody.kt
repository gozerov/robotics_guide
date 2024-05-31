package ru.gozerov.data.login.models

import com.squareup.moshi.Json

data class LoginResponseBody(
    @Json(name = "access_token") val access_token: String,
    @Json(name = "refresh_token") val refresh_token: String
)