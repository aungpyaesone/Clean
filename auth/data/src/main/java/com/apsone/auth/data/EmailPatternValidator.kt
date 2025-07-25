package com.apsone.auth.data

import android.util.Patterns
import com.apsone.domain.PatternValidator

object EmailPatternValidator : PatternValidator{
    override fun matches(value: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(value).matches()
    }

}