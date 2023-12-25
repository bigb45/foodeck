package com.example.domain.use_cases

import com.example.data.models.SignupAuthResponseModel
import com.example.data.models.UserData
import com.example.data.models.UserSignUpModel
import com.example.data.repositories.AuthRepository
import com.example.data.repositories.AuthRepositoryImplCustomApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class CreateUserUseCase @Inject constructor(private val repository: AuthRepositoryImplCustomApi) {
    operator fun invoke(user: UserSignUpModel): Flow<SignupAuthResponseModel> {
        return repository.createUser(user)

    }
}