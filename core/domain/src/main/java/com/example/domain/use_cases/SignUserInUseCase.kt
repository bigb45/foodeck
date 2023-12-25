package com.example.domain.use_cases

import com.example.data.models.LoginAuthResponseModel
import com.example.data.models.UserLoginCredentials
import com.example.data.repositories.AuthRepositoryImplCustomApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SignUserInUseCase @Inject constructor(private val authRepository: AuthRepositoryImplCustomApi) {
    suspend operator fun invoke(user: UserLoginCredentials): Flow<LoginAuthResponseModel> {
        return authRepository.signUserIn(user)
    }
}