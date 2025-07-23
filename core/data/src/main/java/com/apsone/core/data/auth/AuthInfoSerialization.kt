package com.apsone.core.data.auth

import kotlinx.serialization.Serializable
@OptIn(kotlinx.serialization.InternalSerializationApi::class)
@Serializable
data class AuthInfoSerialization(
    val accessToken: String,
    val refreshToken: String,
    val userId: String,
)
