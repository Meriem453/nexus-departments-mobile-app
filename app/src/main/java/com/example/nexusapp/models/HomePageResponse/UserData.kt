package com.example.nexusapp.models.HomePageResponse

import androidx.annotation.Keep

@Keep data class UserData(
    val department_id: Int,
    val email: String,
    val image: String,
    val name: String,
    val role: String
)