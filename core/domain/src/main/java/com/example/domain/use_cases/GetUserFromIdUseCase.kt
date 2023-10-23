package com.example.domain.use_cases

import com.example.data.data.UserData
import com.example.data.repositories.AuthRepository
import javax.inject.Inject

class GetUserFromIdUseCase @Inject constructor(private val repository: AuthRepository){
    suspend operator fun invoke(userId: String): UserData {
        val user = repository.getUser(userId)
        return UserData(
            userId = user?.uid,
            profilePictureUrl = user?.photoUrl.toString(),
            username = user?.displayName
        )
    }
}