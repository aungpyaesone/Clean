package com.apsone.clean

import android.app.Application
import com.apsone.auth.data.di.authDataModule
import com.apsone.auth.presentation.di.authVideoModelModule
import com.apsone.clean.di.appModule
import com.apsone.core.data.di.coreDataModule
import com.apsone.core.database.di.databaseModule
import com.apsone.run.di.locationModule
import com.apsone.run.presentation.di.runViewModelModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class CleanApp : Application(){
    val applicationScope = CoroutineScope(SupervisorJob())
    override fun onCreate() {
        super.onCreate()
        if(BuildConfig.DEBUG){
            Timber.plant(Timber.DebugTree())
        }
        startKoin {
            androidLogger()
            androidContext(this@CleanApp)
            modules(
                authDataModule,
                authVideoModelModule,
                appModule,
                coreDataModule,
                runViewModelModule,
                locationModule,
                databaseModule
            )
        }
    }



}