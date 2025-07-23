package com.apsone.auth.presentation.di

import com.apsone.auth.presentation.login.LoginViewModel
import com.apsone.auth.presentation.register.RegisterViewModel
import com.apsone.domain.PatternValidator
import com.apsone.domain.UserDataValidator
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val authVideoModelModule = module {
    viewModelOf(::RegisterViewModel)
    viewModelOf(::LoginViewModel)
}