package com.example.authentication

import com.example.data.data.UserData
import com.example.data.models.ErrorCode

//sealed interface AuthResult {
//    object Loading : AuthResult
//    object Cancelled : AuthResult
//    object SignedOut : AuthResult
//    data class Error(
//        val errorMessage: String,
//        val errorCode: ErrorCode = ErrorCode.OTHER
//    ) : AuthResult
//
//    data class Success(
//        val data: UserData
//    ) : AuthResult
//
//}