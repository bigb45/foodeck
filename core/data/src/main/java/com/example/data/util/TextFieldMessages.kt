package com.example.data.util

import androidx.annotation.StringRes
import com.example.data.R

// TODO: move this to separate file
enum class TextFieldMessages(@StringRes val message: Int) {
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
    UNKNOWN_ERROR(R.string.unknown_error)
}