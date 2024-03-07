package com.example.nexusapp.models

import androidx.annotation.Keep
import androidx.compose.ui.graphics.Color

@Keep data class TeamResponse(
    val id:Int,
    var name:String,
    var color: String = "0xffffffff"
)
