package com.example.data.repositories

import android.security.keystore.UserNotAuthenticatedException
import android.util.Log.d
import com.example.data.api_services.RestaurantsApiService
import com.example.data.models.OffersDto
import com.example.data.models.OffersModel
import com.example.data.models.RestaurantDto
import com.example.data.models.RestaurantModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class RestaurantsRepositoryImpl @Inject constructor(private val apiService: RestaurantsApiService) : RestaurantsRepository {
    override suspend fun getRestaurants(): Flow<List<RestaurantDto>> {
        val res = handleRequest { apiService.getAllRestaurants() }
        return flow{ emit(res.toDto { it.toDto() }) }
    }

    override suspend fun getOffers(): Flow<List<OffersDto>> {
        val res = handleRequest { apiService.getOffers() }
        return flow{emit(res.toDto { it.toDto() })}
    }

    private suspend fun <T> handleRequest(apiRequest: suspend () -> Response<T>): T{
        try{
            val res = apiRequest()
            return when {

                res.isSuccessful -> {
                    d("error", "${res.body()}")
                    res.body()!!
                }

                res.code() == 403 -> throw(UserNotAuthenticatedException())
                else -> {
                    d("else error", res.message())
                    throw(Exception("Unknown error"))
                }
            }
        }catch (e: IOException){
            d("error", "Network Error ${e.message}")

            throw(e)
        }catch (e: Exception){
            d("error", "Unknown Error ${e.message}")
            throw(e)
        }
    }

}


fun <T, R> List<T>.toDto(converter: (T) -> R): List<R> {
    return map { converter(it) }
}

