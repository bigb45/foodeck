package com.example.data.util

import androidx.annotation.StringRes
import com.example.fooddelivery.R
// TODO: fix this issue
import javax.inject.Inject

val EMAIL_PATTERN = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+".toRegex()

class ValidationUtil @Inject constructor() {
    fun validateEmail(email: String): Boolean {
        return EMAIL_PATTERN.matches(email)
    }

    fun validatePassword(password: String): Boolean {
        return (password.any {
            it.isLetter()
        } && password.any {
            it.isDigit()
        })

    }

    fun validatePhoneNumber(phoneNumber: String): Boolean {
        // TODO: implement phone number validation per country, must implement country code picker first.
        return !phoneNumber.any {
            it.isLetter()
        }
    }


}

enum class ValidationResult(@StringRes val message: Int) {
    PASSWORD_LENGTH(R.string.password_length_error),
    EMPTY_PASSWORD(R.string.empty_password_error),
    WRONG_PATTERN(R.string.wrong_email_pattern_error),
    EMPTY_EMAIL(R.string.empty_email_error),
    PASSWORD_INVALID(R.string.password_invalid_error),
    EMPTY_PHONE_NUMBER(R.string.empty_phone_number_error),
    PHONE_NUMBER_INVALID(R.string.phone_number_invalid_error),
    EMPTY_USERNAME(R.string.empty_username_error),
    DUPLICATE_EMAIL(R.string.duplicate_email_error),
    VALIDATION_FAILED(R.string.unknown_error),
    DUPLICATE_PHONE_NUMBER(R.string.duplicate_phone_number_error),
    PASSWORD_INCORRECT(R.string.incorrect_password_error),
    UNREGISTERED_USER(R.string.unregistered_user_error),
    INVALID_CREDENTIALS(R.string.invalid_credentials_error),
}
