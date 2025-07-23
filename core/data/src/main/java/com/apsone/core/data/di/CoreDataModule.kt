package com.apsone.core.data.di

import android.content.SharedPreferences
import com.apsone.core.data.auth.EncryptedSessionStorage
import com.apsone.core.data.networking.HttpClientFactory
import com.apsone.core.domain.SessionStorage
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val coreDataModule = module{
    single{
        HttpClientFactory(get( )).build()
    }

    singleOf(::EncryptedSessionStorage).bind<SessionStorage>()

}