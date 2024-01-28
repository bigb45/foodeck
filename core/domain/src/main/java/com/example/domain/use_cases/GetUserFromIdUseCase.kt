package com.example.domain.use_cases

import com.example.data.models.UserDetails
import com.example.data.repositories.UsersRepository
import javax.inject.Inject

class GetUserFromIdUseCase @Inject constructor(private val repository: UsersRepository){
    suspend operator fun invoke(userId: String): UserDetails {
            return repository.getUser(userId)

    }
}