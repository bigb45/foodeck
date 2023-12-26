package com.example.domain.use_cases

import com.example.data.models.UserData
import com.example.data.repositories.AuthRepository
import javax.inject.Inject
import javax.inject.Named

class GetUserFromIdUseCase @Inject constructor(@Named("customApi") private val repository: AuthRepository){
    suspend operator fun invoke(userId: String): UserData {
            return repository.getUserById(userId)

    }
}