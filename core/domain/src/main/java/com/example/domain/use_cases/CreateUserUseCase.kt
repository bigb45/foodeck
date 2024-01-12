package com.example.domain.use_cases

import com.example.data.models.SignupAuthResponseModel
import com.example.data.models.UserSignUpModel
import com.example.data.repositories.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Named


class CreateUserUseCase @Inject constructor(@Named("customApi") private val repository: AuthRepository) {
    suspend operator fun invoke(user: UserSignUpModel): Flow<SignupAuthResponseModel> {
        return repository.createUser(user)

    }
}