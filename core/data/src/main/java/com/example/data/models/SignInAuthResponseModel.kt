package com.example.data.models

sealed class SignInAuthResponseModel{
    data class SignInSuccess(val tokens: AuthenticationResponseDto): SignInAuthResponseModel()
    data class SignInFailure(val httpCode: Int): SignInAuthResponseModel()
    object UserNotFound: SignInAuthResponseModel()
    object Loading: SignInAuthResponseModel()
    object InvalidCredentials: SignInAuthResponseModel()
    object InternalServerError: SignInAuthResponseModel()
}

