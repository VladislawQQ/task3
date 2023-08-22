package com.example.task3.ui.fragment.authentication

import android.os.Bundle
import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.task3.R
import com.example.task3.databinding.FragmentAuthBinding
import com.example.task3.ui.utils.Constants.PASSWORD_LENGTH
import com.example.task3.ui.utils.Constants.REGEX_DIGITS
import com.example.task3.ui.utils.Constants.REGEX_UPPER_CASE

class AuthenticationFragment : Fragment(R.layout.fragment_auth) {

    private lateinit var binding: FragmentAuthBinding
    private val viewModel: SignUpViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAuthBinding.bind(view)

        // Email and password validation
        emailErrorChanges()
        passwordErrorChanges()

        setListeners()
    }

    private fun setListeners() {
        with(binding) {
            fragmentAuthButtonRegister.setOnClickListener { registerButton() }
            fragmentAuthButtonGoogle.setOnClickListener { googleButtonAction() }
        }
    }

    private fun googleButtonAction() {
        startNextFragment()
    }

    private fun registerButton() {
        if (!fieldsIsEmpty()) {
            startNextFragment()
        }
    }

    private fun startNextFragment() {
        val email = binding.fragmentAuthEditTextEmail.text.toString()

        val direction = AuthenticationFragmentDirections
            .actionAuthenticationFragmentToMyProfileFragment(email)

        findNavController().navigate(direction)
    }

    private fun emailErrorChanges() {
        with(binding) {
            fragmentAuthEditTextEmail.doAfterTextChanged {
                val email = binding.fragmentAuthEditTextEmail.text.toString()
                fragmentAuthContainerEmail.error =
                    if (viewModel.emailIsValid(email))
                        getString(R.string.invalid_email_address)
                    else
                        null
            }
        }
    }


    private fun passwordErrorChanges() {
        with(binding) {
            fragmentAuthEditTextPassword.doAfterTextChanged {
                fragmentAuthContainerPassword.error = passwordIsValid() // TODO: fragment -> view model -> object
            }
        }
    }

    /**
     * Check if password is valid with regex
     * if !isValid return String
     * else return null
     */
    private fun passwordIsValid(): String? {// TODO: fragment -> view model -> object
        val passwordText = binding.fragmentAuthEditTextPassword.text.toString()

        return if (passwordText.contains(" ")) {
            getString(R.string.dont_use_spaces)
        } else if (passwordText.length < PASSWORD_LENGTH) {
            getString(R.string.min_chars_pass, PASSWORD_LENGTH)
        } else if (!REGEX_UPPER_CASE.toRegex().containsMatchIn(passwordText)) {
            getString(R.string.contain_upper_case_chars)
        } else if (!REGEX_DIGITS.toRegex().containsMatchIn(passwordText)) {
            getString(R.string.contain_number_chars)
        } else null
    }

    private fun fieldsIsEmpty(): Boolean { // TODO: fragment -> view model -> object
        with(binding) {
            val validEmail = fragmentAuthContainerEmail.error == null
            val validPassword = fragmentAuthContainerPassword.error == null
            val emailIsEmpty = fragmentAuthEditTextEmail.text.toString().isEmpty()       // TODO: not need, just validation
            val passwordIsEmpty = fragmentAuthEditTextPassword.text.toString().isEmpty() // TODO: not need, just validation

            return validEmail || validPassword
        }
    }
}