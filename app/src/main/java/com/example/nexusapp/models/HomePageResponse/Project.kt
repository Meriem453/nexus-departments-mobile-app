package com.example.nexusapp.models.HomePageResponse

import androidx.annotation.Keep

@Keep data class Project(
    val progress: Int,
    val title: String,
    val id:Int
)