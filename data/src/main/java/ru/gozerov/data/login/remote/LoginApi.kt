package ru.gozerov.data.login.remote

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import ru.gozerov.data.login.models.LoginRequestBody
import ru.gozerov.data.login.models.LoginResponseBody
import ru.gozerov.domain.models.login.User

interface LoginApi {

    @POST("users/login")
    suspend fun login(@Body loginRequestBody: LoginRequestBody): LoginResponseBody

}