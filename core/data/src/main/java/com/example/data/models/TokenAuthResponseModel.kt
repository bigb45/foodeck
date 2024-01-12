package com.example.data.models

sealed class TokenAuthResponseModel{
    data class SignInSuccess(val tokens: AuthenticationResponseDto): TokenAuthResponseModel()
    data class SignInFailure(val httpCode: Int): TokenAuthResponseModel()
    object Loading: TokenAuthResponseModel()
    object InternalServerError: TokenAuthResponseModel()
    object UnknownError: TokenAuthResponseModel()

}

