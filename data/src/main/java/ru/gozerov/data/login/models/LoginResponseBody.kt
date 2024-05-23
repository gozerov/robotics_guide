package ru.gozerov.data.login.models

import com.squareup.moshi.Json

data class LoginResponseBody(
    @Json(name = "access_token") val accessToken: String,
    @Json(name = "refresh_token") val refreshToken: String
)