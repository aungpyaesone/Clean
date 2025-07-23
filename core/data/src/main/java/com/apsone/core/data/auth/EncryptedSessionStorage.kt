package com.apsone.core.data.auth

import android.content.SharedPreferences
import com.apsone.core.domain.AuthInfo
import com.apsone.core.domain.SessionStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import androidx.core.content.edit
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class EncryptedSessionStorage(
    private val sharePreference: SharedPreferences
): SessionStorage {

    companion object{
        private const val KEY_AUTH_INFO = "KEY_AUTH_INFO"
    }
    override suspend fun get(): AuthInfo? {
        return withContext(Dispatchers.IO){
            val json = sharePreference.getString(KEY_AUTH_INFO,null) ?: return@withContext null
            Json.decodeFromString<AuthInfoSerialization>(json).toAuthInfo()
        }
    }

    override suspend fun set(info: AuthInfo?) {
        withContext(Dispatchers.IO) {
            if(info == null){
                sharePreference.edit(commit = true) { remove(KEY_AUTH_INFO) }
                return@withContext
            }

            val json = Json.encodeToString(info.toAuthInfoSerialization())
            sharePreference.edit(commit = true) { putString(KEY_AUTH_INFO, json) }

        }
    }

}