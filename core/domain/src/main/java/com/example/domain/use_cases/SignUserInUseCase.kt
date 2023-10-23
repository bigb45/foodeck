package com.example.domain.use_cases

import com.example.data.models.AuthResult
import com.example.data.data.UserLoginCredentials
import com.example.data.repositories.AuthRepository
import javax.inject.Inject

class SignUserInUseCase @Inject constructor(private val authRepository: AuthRepository) {
    suspend operator fun invoke(user: UserLoginCredentials): AuthResult {
        return authRepository.signUserIn(user)
    }
}