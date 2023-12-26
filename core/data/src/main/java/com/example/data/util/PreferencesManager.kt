package com.example.data.util

import android.content.Context
import android.content.SharedPreferences
import android.util.Log.d

class PreferencesManager(private val context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
    fun writeToken(name: Int, token: String){
        with(sharedPreferences.edit()){
            putString(context.getString(name), token)
            apply()
        }
    }


    fun getToken(name: Int): String?{
        return sharedPreferences.getString(context.getString(name), null)
    }
}