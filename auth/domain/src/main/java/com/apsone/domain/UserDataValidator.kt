package com.apsone.domain

class UserDataValidator(
    private val patternValidator: PatternValidator
) {

    fun isValidEmail(email: String): Boolean {
        return patternValidator.matches(email.trim() )
    }

    fun isValidPassword(password: String): PasswordValidationState {
        val hasMinLength =  password.length >= MIN_PASSWORD_LENGTH
        val hasNumber = password.any { it.isDigit() }
        val hasLowerCased = password.any { it.isLowerCase() }
        val hasUpperCased = password.any { it.isUpperCase() }
        return PasswordValidationState(
            hasMinLength = hasMinLength,
            hasNumber = hasNumber,
            hasLowerCased = hasLowerCased,
            hasUpperCased = hasUpperCased
        )
    }

    companion object{
        const val MIN_PASSWORD_LENGTH = 9
    }
}