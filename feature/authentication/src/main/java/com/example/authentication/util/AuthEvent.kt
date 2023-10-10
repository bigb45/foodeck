package com.example.authentication.util

sealed class AuthEvent {
    data class EmailChanged(val newEmail: String) : AuthEvent()
    data class PasswordChanged(val newPassword: String) : AuthEvent()
    data class PhoneNumberChanged(val newPhoneNumber: String) : AuthEvent()
    data class UsernameChanged(val newUsername: String) : AuthEvent()
    object Submit : AuthEvent()

}