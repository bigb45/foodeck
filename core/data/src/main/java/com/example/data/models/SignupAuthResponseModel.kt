package com.example.data.models


// TODO: Add error message to the error states
sealed class SignupAuthResponseModel {
    data class SignupSuccess(val tokens: AuthenticationResponseDto): SignupAuthResponseModel()
    data class SignupFailure(val httpCode: Int): SignupAuthResponseModel()
    object UserAlreadyExists: SignupAuthResponseModel()
    object PhoneNumberAlreadyExists: SignupAuthResponseModel()
    object Loading: SignupAuthResponseModel()
    object InternalServerError: SignupAuthResponseModel()
    object UnknownError: SignupAuthResponseModel()
}