package com.example.nexusapp.models

import androidx.compose.ui.graphics.Color

data class TeamResponse(
    val id:Int,
    var name:String,
    var color: String = "0xffffffff"
)
