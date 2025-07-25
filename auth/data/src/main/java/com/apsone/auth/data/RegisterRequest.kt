
package com.apsone.auth.data

import android.annotation.SuppressLint
import kotlinx.serialization.Serializable
@OptIn(kotlinx.serialization.InternalSerializationApi::class)
@Serializable
data class RegisterRequest(
    val email: String,
    val password: String
)