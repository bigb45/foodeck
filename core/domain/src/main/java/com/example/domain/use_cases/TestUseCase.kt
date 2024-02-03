package com.example.domain.use_cases

import com.example.data.entities.OrderSelection
import com.example.data.repositories.RestaurantsRepository
import javax.inject.Inject

class TestUseCase @Inject constructor(private val repository: RestaurantsRepository) {

   suspend operator fun invoke(selections: OrderSelection): Boolean{
       return repository.saveUserMenuSelections(selections)
   }
}