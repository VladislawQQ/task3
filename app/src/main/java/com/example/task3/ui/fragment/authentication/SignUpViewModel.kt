package com.example.task3.ui.fragment.authentication

import android.util.Patterns
import androidx.lifecycle.ViewModel
import com.example.task3.R
import com.example.task3.ui.utils.Validation

class SignUpViewModel : ViewModel() {
    /**
     * Check if email is valid with Patterns.EMAIL_ADDRESS
     * if !isValid return "Invalid Email Address"
     * else return null
     */
    fun emailIsValid(email: String): Boolean = Validation.emailIsValid(email)
}