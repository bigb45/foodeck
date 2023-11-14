package com.example.domain.use_cases

import com.example.data.models.UserData
import com.example.data.models.UserSignUpModel
import com.example.data.repositories.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class SignUserUpUseCase @Inject constructor(private val repository: AuthRepository) {
    operator fun invoke(user: UserSignUpModel): Flow<UserData> {
        return repository.createUser(user)

    }
}