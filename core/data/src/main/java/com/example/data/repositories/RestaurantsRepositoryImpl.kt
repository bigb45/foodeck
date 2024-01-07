package com.example.data.repositories

import android.util.Log.d
import com.example.data.api_services.RestaurantsApiService
import com.example.data.models.RestaurantDto
import com.example.data.models.RestaurantModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RestaurantsRepositoryImpl @Inject constructor(private val apiService: RestaurantsApiService) : RestaurantsRepository {
    override suspend fun getRestaurants(): Flow<List<RestaurantDto>> {
        return try{
            val res = apiService.getAllRestaurants()
            d("error", "${res.code()}, ${res.message()}" )
            flow{
                emit(
                    when {
                        res.isSuccessful -> {
                            d("error", res.body().toString())
                            res.body()!!.toRestaurantDto()
                        }

                        res.code() == 403 -> {
                            throw(Exception("unauthorized"))
                        }

                        else -> {
                            d("else error", res.message())
                            emptyList<RestaurantDto>()
//                            throw(Exception("Unknown error"))
                        }
                    }
                )
            }
        }catch (e: Exception){
            d("error", "caught error ${e.message}")
           flow { throw (e) }
        }


    }
}

fun List<RestaurantModel>.toRestaurantDto(): List<RestaurantDto>{
    val convertedList: ArrayList<RestaurantDto> = ArrayList()
    this.forEach {
        convertedList.add(it.toDto())
    }
    return convertedList.toList()
}