package com.example.domain.use_cases

import com.example.data.models.UserDetails
import com.example.data.repositories.AuthRepository
import javax.inject.Inject
import javax.inject.Named

@Deprecated("No need in for this use case in Custom API")
class AddUserInformationUseCase @Inject constructor(@Named("customApi") private val repository: AuthRepository) {
    suspend operator fun invoke(userData: UserDetails){
        repository.addUserInformation(userData)
    }
}