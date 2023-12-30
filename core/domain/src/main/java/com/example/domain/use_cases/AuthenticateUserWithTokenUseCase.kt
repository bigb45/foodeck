package com.example.domain.use_cases
import com.example.data.models.TokenAuthResponseModel
import com.example.data.repositories.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Named
class AuthenticateUserWithTokenUseCase @Inject constructor(@Named("customApi") private val repository: AuthRepository){
    suspend operator fun invoke(token: String, provider: String): Flow<TokenAuthResponseModel>{
        return repository.authenticateUserWithToken(token, provider)
    }
}


