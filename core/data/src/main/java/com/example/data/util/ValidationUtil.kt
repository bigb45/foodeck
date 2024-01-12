package com.example.data.util

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

