package com.apsone.auth.data.di

import com.apsone.auth.data.AuthRepositoryImpl
import com.apsone.auth.data.EmailPatternValidator
import com.apsone.domain.AuthRepository
import com.apsone.domain.PatternValidator
import com.apsone.domain.UserDataValidator
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val authDataModule = module{
    single<PatternValidator>{
        EmailPatternValidator
    }
    singleOf(::UserDataValidator)
    singleOf(::AuthRepositoryImpl).bind<AuthRepository>()
}