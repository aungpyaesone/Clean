package com.apsone.domain

import com.apsone.core.domain.util.DataError
import com.apsone.core.domain.util.EmptyResult

interface AuthRepository {
    suspend fun register(email: String, password: String) : EmptyResult<DataError.Network>
    suspend fun login(email: String,password: String): EmptyResult<DataError.Network>
}