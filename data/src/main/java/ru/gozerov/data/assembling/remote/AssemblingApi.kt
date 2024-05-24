package ru.gozerov.data.assembling.remote

import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query
import ru.gozerov.data.assembling.remote.models.AssemblingDTO
import ru.gozerov.data.assembling.remote.models.ComponentDTO
import ru.gozerov.data.assembling.remote.models.ContainerDTO
import ru.gozerov.domain.models.assembling.SimpleAssembling

interface AssemblingApi {

    @GET("containers/{container_number}")
    suspend fun getContainer(
        @Header("Authorization") bearer: String,
        @Path("container_number") number: String
    ): ContainerDTO

    @PATCH("containers/{container_number}")
    suspend fun updateContainer(
        @Header("Authorization") bearer: String,
        @Body containerDTO: ContainerDTO
    )

    @GET("assemblies")
    suspend fun getAssemblies(@Header("Authorization") bearer: String): List<SimpleAssembling>

    @GET("assemblies/{assemble_id}")
    suspend fun getAssemblingById(
        @Header("Authorization") bearer: String,
        @Path("assemble_id") id: Int
    ): AssemblingDTO

    @GET("components/{component_id}")
    suspend fun getComponentById(
        @Header("Authorization") bearer: String,
        @Path("component_id") id: Int
    ): ComponentDTO

    @PATCH("components/{component_id}")
    suspend fun updateComponent(
        @Header("Authorization") bearer: String,
        @Path("component_id") id: Int,
        @Query("name") componentName: String
    )

    @POST("components/{component_id}/upload_photo")
    suspend fun uploadComponentPhoto(
        @Header("Authorization") bearer: String,
        @Path("component_id") id: Int,
        @Part image: MultipartBody.Part? = null
    )

}