package ru.gozerov.data.login.remote

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import ru.gozerov.domain.models.login.User

interface LabApi {

    @GET("user/{id}")
    suspend fun getOwnUser(
        @Header("Authorization") bearer: String,
        @Path(value = "id") id: String
    ): User

}