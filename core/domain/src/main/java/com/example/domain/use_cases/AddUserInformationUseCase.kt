package com.example.domain.use_cases

import com.example.data.models.UserDetailsModel
import com.example.data.repositories.AuthRepository
import javax.inject.Inject
import javax.inject.Named

class AddUserInformationUseCase @Inject constructor(@Named("customApi") private val repository: AuthRepository) {
    suspend operator fun invoke(userData: UserDetailsModel){
        repository.addUserInformation(userData)
    }
}