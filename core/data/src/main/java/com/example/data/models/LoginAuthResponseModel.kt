package com.example.data.models

import com.example.data.api_services.TokenDto

sealed class LoginAuthResponseModel{
    data class LoginSuccess(val tokens: TokenDto): LoginAuthResponseModel()
    data class LoginFailure(val httpCode: Int): LoginAuthResponseModel()
    object UserNotFound: LoginAuthResponseModel()
    object Loading: LoginAuthResponseModel()
    object InvalidCredentials: LoginAuthResponseModel()
    object InternalServerError: LoginAuthResponseModel()
    object UnknownError: LoginAuthResponseModel()

}
