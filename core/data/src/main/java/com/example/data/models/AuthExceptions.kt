package com.example.data.models

interface ApiException
class InvalidCredentialsException(message: String): Exception(message)
class UserNotFoundException(message: String): Exception(message)
class InternalServerException(message: String): Exception(message), ApiException
class UnknownException(message: String): Exception(message), ApiException
