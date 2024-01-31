package com.example.data.repositories

import android.security.keystore.UserNotAuthenticatedException
import android.util.Log.d
import com.example.data.api_services.RestaurantsApiService
import com.example.data.entities.CartItem
import com.example.data.entities.OrderSelection
import com.example.data.local.database.Database
import com.example.data.models.CartItemDto
import com.example.data.models.InternalServerException
import com.example.data.models.Offer
import com.example.data.models.OptionsSectionDto
import com.example.data.models.Restaurant
import com.example.data.models.RestaurantMenu
import com.example.data.models.toEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class RestaurantsRepositoryImpl @Inject constructor(private val apiService: RestaurantsApiService, roomDatabase: Database) :
    RestaurantsRepository {
    private val userDao = roomDatabase.userDao()
    private val orderDao = roomDatabase.orderDao()
    override suspend fun getRestaurants(): Flow<List<Restaurant>> {
        return try {
            val res = handleRequest { apiService.getAllRestaurants() }
            flow { emit(res) }
        } catch (e: Exception) {
            flow { throw (e) }
        }
    }

    override suspend fun getRestaurantMeals(restaurantId: String): Flow<List<RestaurantMenu>> {
        return try {
            val res = handleRequest { apiService.getMealsByRestaurantId(restaurantId) }
            flow { emit(res) }
        } catch (e: Exception) {
            flow { throw (e) }
        }
    }


    override suspend fun getOffers(): Flow<List<Offer>> {
        return try {
            val res = handleRequest { apiService.getOffers() }
            flow { emit(res) }
        } catch (e: Exception) {
            flow { throw ((e)) }
        }
    }


    override suspend fun saveUserMenuSelections(selection: OrderSelection): Boolean {
//        val queryResult = userDao.getAll()
//        log(queryResult.toString())
//        userDao.insertMohammed(UserTest("1", "Mohammed", "Natour"))
        orderDao.insertOrderSelection(selection)
        return false
    }

    override suspend fun saveCartItem(cartItem: CartItemDto) {
        orderDao.insertCartItem(cartItem.toEntity())
    }

    override suspend fun getMealOptions(restaurantId: String, menuId: String): Flow<List<OptionsSectionDto>> {
        return try {
            val res = handleRequest { apiService.getMealSections(
                restaurantId = restaurantId,
                menuId = menuId
            ) }
            flow { emit(res.sections) }
        } catch (e: Exception) {
            d("error", "${e.message}")
            flow { throw (e) }
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
            throw (Exception("An unknown exception has occur    red"))
        }
    }
}


class NetworkError(message: String) : Exception(message)