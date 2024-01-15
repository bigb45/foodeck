package com.example.data.repositories

import android.security.keystore.UserNotAuthenticatedException
import android.util.Log.d
import com.example.data.api_services.RestaurantsApiService
import com.example.data.models.InternalServerException
import com.example.data.models.OffersDto
import com.example.data.models.RestaurantDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class RestaurantsRepositoryImpl @Inject constructor(private val apiService: RestaurantsApiService) :
    RestaurantsRepository {
    override suspend fun getRestaurants(): Flow<List<RestaurantDto>> {
        return try {
            val res = handleRequest { apiService.getAllRestaurants() }
            flow { emit(res.toDto { it.toDto() }) }
        } catch (e: Exception) {
            flow { throw (e) }
        }
    }

    override suspend fun getOffers(): Flow<List<OffersDto>> {
        return try {
            val res = handleRequest { apiService.getOffers() }
            flow { emit(res.toDto { it.toDto() }) }
        } catch (e: Exception) {
            flow { throw ((e)) }
        }
    }

    private suspend fun <T> handleRequest(apiRequest: suspend () -> Response<T>): T {
        try {
            val res = apiRequest()
            d("error", "${res.code()}, ${res.message()}")

        return when {
                res.isSuccessful -> {
                    d("error", "${res.body()}")
                    res.body()!!
                }

                res.code() == 403 -> throw (UserNotAuthenticatedException())

                res.code() == 500 -> throw (InternalServerException("An internal server exception has occurred"))

                else -> {
                    d("error", "${res.code()}, ${res.message()}")
                    throw (Exception("Unknown error"))
                }
            }
        } catch (e: IOException) {
            throw (NetworkError("Error while fetching data."))
        } catch (e: Exception) {
            d("error", "Unknown error ${e.message}")
            throw (Exception("An unknown exception has occurred"))
        }
    }
}


fun <T, R> List<T>.toDto(converter: (T) -> R): List<R> {
    return map { converter(it) }
}

class NetworkError(message: String) : Exception(message)