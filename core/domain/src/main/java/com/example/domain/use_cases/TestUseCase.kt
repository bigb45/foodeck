package com.example.domain.use_cases

import com.example.data.entities.UserTest
import com.example.data.repositories.RestaurantsRepository
import javax.inject.Inject

class TestUseCase@Inject constructor(private val repository: RestaurantsRepository) {

   suspend operator fun invoke(): Boolean{
       return repository.saveUserOrder()
   }
}