package com.example.fooddelivery.domain.use_cases

import com.example.fooddelivery.util.FieldError
import com.example.fooddelivery.util.ValidationResult
import com.example.fooddelivery.util.ValidationUtil
import javax.inject.Inject

class ValidateUsernameUseCase @Inject constructor() {
    operator fun invoke(username: String): FieldError {

        if(username.isEmpty()){
            return FieldError(true, ValidationResult.EMPTY_USERNAME)
        }
        return FieldError(false, null)
    }
}