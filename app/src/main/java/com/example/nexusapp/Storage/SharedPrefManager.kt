package com.example.nexusapp.Storage

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences

class SharedPrefManager(context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

    companion object {
        private const val TOKEN_KEY = "token"
    }

    var token: String?
        get() = sharedPreferences.getString(TOKEN_KEY, null)
        set(value) {
            sharedPreferences.edit().putString(TOKEN_KEY, value).apply()
        }
}
