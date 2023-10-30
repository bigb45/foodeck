package com.example.domain.use_cases

import com.example.data.data.UserData
import com.example.data.repositories.AuthRepository
import javax.inject.Inject

class AddUserInformationUseCase @Inject constructor(private val repository: AuthRepository) {
    suspend operator fun invoke(userData: UserData){
        repository.addUserInformationToDatabase(userData)
    }
}