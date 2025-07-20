package com.apsone.domain

data class PasswordValidationState(
    val hasMinLength: Boolean = false,
    val hasNumber: Boolean = false,
    val hasLowerCased: Boolean = false,
    val hasUpperCased: Boolean = false,
){
    val isValid: Boolean
        get() = hasMinLength && hasNumber && hasLowerCased && hasUpperCased
}
