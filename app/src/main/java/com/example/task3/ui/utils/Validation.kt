package com.example.task3.ui.utils

import android.util.Patterns

object Validation { // TODO: all constants of validation to this place
    fun emailIsValid(email : String): Boolean = Patterns.EMAIL_ADDRESS.matcher(email).matches()
    // TODO: validation password
}