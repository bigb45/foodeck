package com.example.domain.use_cases

import com.example.data.data.UserData
import com.example.data.data.UserLoginCredentials
import com.example.data.repositories.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SignUserInUseCase @Inject constructor(private val authRepository: AuthRepository) {
    operator fun invoke(user: UserLoginCredentials): Flow<UserData> {
        return authRepository.signUserIn(user)
    }
}