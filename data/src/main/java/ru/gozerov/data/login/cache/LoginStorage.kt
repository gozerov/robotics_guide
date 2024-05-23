package ru.gozerov.data.login.cache

interface LoginStorage {

    fun saveLabTokenAndId(token: String, id: String)

    fun saveLoginTokens(accessToken: String, refreshToken: String)

    fun checkIsAuthorized(): Boolean

    fun getLabToken(): String?

    fun getLabId(): String?

    fun clear()

}