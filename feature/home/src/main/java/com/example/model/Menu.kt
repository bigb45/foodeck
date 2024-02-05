package com.example.model

data class Menu(
    val id: String? = "0",
    val name: String,
    val imageUrl: String?,
    val contents: String,
    val price: String,
//    TODO: make [currency] a global variable
    val currency: String,
)