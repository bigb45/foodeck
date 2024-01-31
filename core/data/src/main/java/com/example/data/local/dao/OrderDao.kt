package com.example.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import com.example.data.entities.CartItem
import com.example.data.entities.OrderSelection

@Dao
interface OrderDao{

    @Insert
    fun insertCartItem(cartItem: CartItem)

    @Insert
    fun insertOrderSelection(selection: OrderSelection)
}