package com.apsone.auth.data

import kotlinx.serialization.Serializable
@OptIn(kotlinx.serialization.InternalSerializationApi::class)
@Serializable
data class LoginRequest(
    val email: String,
    val password: String
)