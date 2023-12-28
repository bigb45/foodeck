package com.example.data.models


sealed class SignupAuthResponseModel {
    data class SignupSuccess(val tokens: AuthenticationResponseDto): SignupAuthResponseModel()
    data class SignupFailure(val httpCode: Int): SignupAuthResponseModel()
    object UserAlreadyExists: SignupAuthResponseModel()
    object PhoneNumberAlreadyExists: SignupAuthResponseModel()
    object Loading: SignupAuthResponseModel()
    object InternalServerError: SignupAuthResponseModel()
    object UnknownError: SignupAuthResponseModel()
}