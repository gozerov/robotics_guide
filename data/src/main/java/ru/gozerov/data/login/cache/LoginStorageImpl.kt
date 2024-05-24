package ru.gozerov.data.login.cache

import android.content.SharedPreferences
import javax.inject.Inject

class LoginStorageImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : LoginStorage {

    override fun saveLabTokenAndId(token: String, id: String) {
        sharedPreferences
            .edit()
            .putString(KEY_LAB_TOKEN, token)
            .putString(KEY_LAB_ID, id)
            .apply()
    }

    override fun saveLoginTokens(accessToken: String, refreshToken: String) {
        sharedPreferences
            .edit()
            .putString(KEY_ACCESS_TOKEN, accessToken)
            .putString(KEY_REFRESH_TOKEN, refreshToken)
            .apply()
    }

    override fun checkIsAuthorized(): Boolean {
        val token = sharedPreferences.getString(KEY_LAB_TOKEN, null)
        return token != null
    }

    override fun getLabToken(): String? =
        sharedPreferences.getString(KEY_LAB_TOKEN, null)

    override fun getLabId(): String? =
        sharedPreferences.getString(KEY_LAB_ID, null)

    override fun clear() {
        sharedPreferences
            .edit()
            .clear()
            .apply()
    }

    override fun getAccessToken(): String =
        sharedPreferences.getString(KEY_ACCESS_TOKEN, "").toString()

    companion object {

        private const val KEY_ACCESS_TOKEN = "INNER_ACCESS_TOKEN"
        private const val KEY_LAB_TOKEN = "LAB_TOKEN"
        private const val KEY_LAB_ID = "LAB_ID"
        private const val KEY_REFRESH_TOKEN = "INNER_REFRESH_TOKEN"

    }

}