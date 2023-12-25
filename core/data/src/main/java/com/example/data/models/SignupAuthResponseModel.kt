package com.example.data.models

import com.example.data.api_services.TokenDto

sealed class SignupAuthResponseModel {
    data class SignupSuccess(val tokens: TokenDto): SignupAuthResponseModel()
    data class SignupFailure(val httpCode: Int): SignupAuthResponseModel()
    object UserAlreadyExists: SignupAuthResponseModel()
    object PhoneNumberAlreadyExists: SignupAuthResponseModel()
    object Loading: SignupAuthResponseModel()
    object InternalServerError: SignupAuthResponseModel()
    object UnknownError: SignupAuthResponseModel()
}