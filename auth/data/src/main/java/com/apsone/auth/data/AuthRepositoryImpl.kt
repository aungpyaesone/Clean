package com.apsone.auth.data

import com.apsone.core.data.networking.safeCall
import com.apsone.core.domain.util.DataError
import com.apsone.core.domain.util.EmptyResult
import com.apsone.domain.AuthRepository
import io.ktor.client.HttpClient
import com.apsone.core.data.networking.post

class AuthRepositoryImpl(
    private val httpClient: HttpClient
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
}

