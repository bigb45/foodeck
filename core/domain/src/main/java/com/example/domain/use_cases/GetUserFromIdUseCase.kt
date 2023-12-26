package com.example.domain.use_cases

import com.example.data.models.UserData
import com.example.data.repositories.AuthRepository
import com.example.data.repositories.UsersRepository
import javax.inject.Inject
import javax.inject.Named

class GetUserFromIdUseCase @Inject constructor(private val repository: UsersRepository){
    suspend operator fun invoke(userId: String): UserData {
            return repository.getUser(userId)

    }
}