package com.apsone.domain

interface PatternValidator {
    fun matches(value: String): Boolean
}