package ru.gozerov.domain.repositories

import ru.gozerov.domain.models.login.UserProfile

interface LoginRepository {

    suspend fun saveLabTokenAndId(token: String, id: String)

    suspend fun getOwnProfile(): UserProfile

    suspend fun checkIsAuthorized(): Boolean

    suspend fun logout()

    suspend fun updateAuthorization(): Boolean

}