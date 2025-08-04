package com.apsone.run.di

import com.apsone.run.domain.LocationObserver
import com.apsone.run.location.AndroidLocationObserver
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module

val locationModule = module {
    singleOf(::AndroidLocationObserver).bind<LocationObserver>()

}