package com.example.domain.use_cases

import com.example.data.models.AuthResult
import com.example.data.models.NewUserData
import com.example.data.repositories.AuthRepository
import javax.inject.Inject

class SignUserUpUseCase @Inject constructor(private val repository: AuthRepository) {
    suspend operator fun invoke(user: NewUserData): AuthResult {
        return repository.createUser(user)

    }
}