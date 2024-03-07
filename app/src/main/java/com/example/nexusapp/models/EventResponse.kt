package com.example.nexusapp.models

import androidx.annotation.Keep

@Keep data class EventResponse(
    val id:Int,
    val name:String,
    val date:String,
    val details:String
)
