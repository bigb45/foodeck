package com.example.domain.use_cases

import com.example.data.models.SignInAuthResponseModel
import com.example.data.models.UserSignInInfo
import com.example.data.repositories.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Named

class SignUserInUseCase @Inject constructor(@Named("customApi") private val authRepository: AuthRepository) {
    suspend operator fun invoke(user: UserSignInInfo): Flow<SignInAuthResponseModel> {
        return authRepository.signUserIn(user)
    }
}