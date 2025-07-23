package com.apsone.core.data.auth

import com.apsone.core.domain.AuthInfo

fun AuthInfo.toAuthInfoSerialization(): AuthInfoSerialization {
    return AuthInfoSerialization(
        accessToken = accessToken,
        refreshToken = refreshToken,
        userId = userId
    )
}

fun AuthInfoSerialization.toAuthInfo(): AuthInfo {
    return AuthInfo(
        accessToken = accessToken,
        refreshToken = refreshToken,
        userId = userId
    )
}