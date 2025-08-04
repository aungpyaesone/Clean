package com.apsone.clean.di

import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.apsone.auth.data.EmailPatternValidator
import com.apsone.clean.CleanApp
import com.apsone.clean.MainViewModel
import com.apsone.core.data.auth.EncryptedSessionStorage
import com.apsone.domain.PatternValidator
import com.apsone.domain.UserDataValidator
import kotlinx.coroutines.CoroutineScope
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module{
    single<SharedPreferences> {
        EncryptedSharedPreferences(
            androidApplication(),
            "auth_pref",
            MasterKey(androidApplication()),
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    single<CoroutineScope>{
        (androidApplication() as CleanApp).applicationScope
    }

    viewModelOf(::MainViewModel)

}