package ru.gozerov.data.assembling

import android.content.Context
import android.net.Uri
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import ru.gozerov.data.assembling.cache.AssemblingStorage
import ru.gozerov.data.assembling.remote.AssemblingApi
import ru.gozerov.data.login.cache.LoginStorage
import ru.gozerov.data.toAssembling
import ru.gozerov.data.toComponent
import ru.gozerov.data.toContainer
import ru.gozerov.data.toContainerDTO
import ru.gozerov.domain.models.assembling.Assembling
import ru.gozerov.domain.models.assembling.AssemblyStep
import ru.gozerov.domain.models.assembling.Component
import ru.gozerov.domain.models.assembling.Container
import ru.gozerov.domain.models.assembling.FilterCategory
import ru.gozerov.domain.models.assembling.SimpleAssembling
import ru.gozerov.domain.repositories.AssemblingRepository
import java.io.InputStream
import javax.inject.Inject

class AssemblingRepositoryImpl @Inject constructor(
    private val assemblingApi: AssemblingApi,
    @ApplicationContext private val context: Context,
    private val assemblingStorage: AssemblingStorage,
    private val loginStorage: LoginStorage
) : AssemblingRepository {

    private val categories = listOf("Популярные", "Новые")

    private var currentStep = 1
    private var assemblingInProcess: Assembling? = null

    override suspend fun getSimpleAssemblingList(): Pair<List<SimpleAssembling>, List<SimpleAssembling>> {
        val bearer = "Bearer ${loginStorage.getAccessToken()}"
        val assemblings = assemblingApi.getAssemblies(bearer)
        return assemblings to assemblings
    }

    override suspend fun getAssemblingById(id: Int): Flow<Assembling> = flow {
        val bearer = "Bearer ${loginStorage.getAccessToken()}"
        val cachedAssembling = assemblingStorage.getAssemblingById(id)
        if (cachedAssembling != null) emit(cachedAssembling)
        val assembling = assemblingApi.getAssemblingById(bearer, id)
        assemblingStorage.insertAssembling(assembling)
        emit(assembling.toAssembling())
    }

    override suspend fun searchAssembling(
        query: String,
        category: FilterCategory
    ): List<SimpleAssembling> {
        val bearer = "Bearer ${loginStorage.getAccessToken()}"
        val assemblings = assemblingApi.getAssemblies(bearer)
        return if (query.isBlank())
            assemblings
        else
            assemblings.filter { it.name.lowercase().contains(query.lowercase()) }
    }

    override suspend fun getCategories(): List<String> {
        return categories
    }

    override suspend fun getCurrentStep(assemblingId: Int): AssemblyStep {
        return if (assemblingInProcess != null) {
            val assembling = requireNotNull(assemblingInProcess)
            AssemblyStep(
                container = assembling.components[currentStep - 1],
                step = currentStep,
                stepCount = assembling.components.size,
                isFinish = currentStep == assembling.components.size
            )
        } else {
            val assembling = assemblingStorage.getAssemblingById(id = assemblingId)!!
            assemblingInProcess = assembling
            AssemblyStep(
                container = assembling.components[0],
                step = currentStep,
                stepCount = assembling.components.size,
                isFinish = currentStep == assembling.components.size
            )
        }
    }

    override suspend fun nextStep(back: Boolean) {
        if (back) {
            if (currentStep == 1) {
                assemblingInProcess = null
                return
            }
            currentStep--
        } else {
            assemblingInProcess?.let {
                if (it.components.size == currentStep) {
                    //TODO: запрос на инкремент readyAmount
                    assemblingInProcess = null
                    currentStep = 1
                } else
                    currentStep++
            }
        }
    }

    override suspend fun getComponentById(id: Int): Component {
        val bearer = "Bearer ${loginStorage.getAccessToken()}"
        return assemblingApi.getComponentById(bearer, id).toComponent()
    }

    override suspend fun updateComponent(id: Int, name: String, uri: Uri?) {
        val bearer = "Bearer ${loginStorage.getAccessToken()}"
        assemblingApi.updateComponent(bearer, id, name)
        uri?.let {
            val part = getImagePart(it)
            assemblingApi.uploadComponentPhoto(bearer, id, part)
        }
    }

    override suspend fun getContainerById(number: String): Container {
        val bearer = "Bearer ${loginStorage.getAccessToken()}"
        return assemblingApi.getContainer(bearer, number).toContainer()
    }

    override suspend fun updateContainer(container: Container) {
        val bearer = "Bearer ${loginStorage.getAccessToken()}"
        assemblingApi.updateContainer(bearer, container.toContainerDTO())
    }

    private fun getImagePart(imageUri: Uri?): MultipartBody.Part? {
        var part: MultipartBody.Part? = null
        imageUri?.let { uri ->
            val mimeType = context.contentResolver.getType(uri)
            val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
            val bytes = inputStream?.readBytes() ?: return null
            inputStream.close()
            val requestBody = bytes.toRequestBody(mimeType?.toMediaTypeOrNull())
            part = MultipartBody.Part.createFormData("file", "file.jpeg", requestBody)
        }
        return part
    }


}