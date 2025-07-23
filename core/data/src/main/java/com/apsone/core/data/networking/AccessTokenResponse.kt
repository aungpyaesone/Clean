package com.apsone.core.data.networking

import kotlinx.serialization.Serializable
@OptIn(kotlinx.serialization.InternalSerializationApi::class)
@Serializable
data class AccessTokenResponse(
    val accessToken: String,
    val expireTimestamp: String
)
