package com.apsone.core.data.networking

import kotlinx.serialization.Serializable

@OptIn(kotlinx.serialization.InternalSerializationApi::class)
@Serializable
data class AccessTokenRequest(
    val refreshToken: String,
    val userId: String
)