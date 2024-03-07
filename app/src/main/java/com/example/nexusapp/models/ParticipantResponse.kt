package com.example.nexusapp.models

import androidx.annotation.Keep

@Keep data class ParticipantResponse(
    val name:String,
    val team:String,
    val came:String
)
