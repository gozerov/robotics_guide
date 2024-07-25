package ru.gozerov.data.login.remote

import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import ru.gozerov.data.login.models.LoginRequestBody
import ru.gozerov.data.login.models.LoginResponseBody

interface LoginApi {

    @POST("users/login")
    suspend fun login(@Body loginRequestBody: LoginRequestBody): LoginResponseBody

    @POST("users/refresh")
    suspend fun refresh(@Header("Authorization") refreshToken: String): LoginResponseBody


}