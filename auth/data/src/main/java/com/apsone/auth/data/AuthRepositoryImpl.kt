package com.apsone.auth.data

import com.apsone.core.data.networking.safeCall
import com.apsone.core.domain.util.DataError
import com.apsone.core.domain.util.EmptyResult
import com.apsone.domain.AuthRepository
import io.ktor.client.HttpClient
import com.apsone.core.data.networking.post
import com.apsone.core.domain.AuthInfo
import com.apsone.core.domain.SessionStorage
import com.apsone.core.domain.util.Result
import com.apsone.core.domain.util.asEmptyDataResult

class AuthRepositoryImpl(
    private val httpClient: HttpClient,
    private val sessionManager: SessionStorage
) : AuthRepository {

    override suspend fun register(
        email: String,
        password: String
    ): EmptyResult<DataError.Network> {
        return httpClient
            .post<RegisterRequest, Unit>(
                route = "/register",
                body = RegisterRequest(email, password)
            )
    }

    override suspend fun login(
        email: String,
        password: String
    ): EmptyResult<DataError.Network> {
        val result = httpClient
            .post<LoginRequest, LoginResponse>(
                route = "/login",
                body = LoginRequest(email, password)
            )
        if(result is Result.Success){
            sessionManager.set(
                AuthInfo(
                    accessToken = result.data.accessToken,
                    refreshToken = result.data.refreshToken,
                    userId = result.data.userId
                )
            )
        }
        return result.asEmptyDataResult()
    }
}

