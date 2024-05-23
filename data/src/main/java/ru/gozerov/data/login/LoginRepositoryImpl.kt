package ru.gozerov.data.login

import ru.gozerov.data.login.cache.LoginStorage
import ru.gozerov.data.login.remote.LabApi
import ru.gozerov.data.login.remote.LoginApi
import ru.gozerov.data.toLoginRequestBody
import ru.gozerov.data.toUserProfile
import ru.gozerov.domain.models.login.UserProfile
import ru.gozerov.domain.repositories.LoginRepository
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val loginApi: LoginApi,
    private val labApi: LabApi,
    private val loginStorage: LoginStorage
) : LoginRepository {

    override suspend fun saveLabTokenAndId(token: String, id: String) {
        val user = labApi.getOwnUser(token, id)
        loginStorage.saveLabTokenAndId(token, id)
        val response = loginApi.login(user.toLoginRequestBody())
        loginStorage.saveLoginTokens(response.accessToken, response.refreshToken)
    }

    override suspend fun getOwnProfile(): UserProfile {
        val bearer = loginStorage.getLabToken()
        val id = loginStorage.getLabId()

        if (bearer == null || id == null)
            error("unexpected bearer or id")

        return labApi.getOwnUser(bearer, id).toUserProfile()
    }

    override suspend fun checkIsAuthorized(): Boolean {
        return loginStorage.checkIsAuthorized()
    }

    override suspend fun logout() {
        loginStorage.clear()
    }

}