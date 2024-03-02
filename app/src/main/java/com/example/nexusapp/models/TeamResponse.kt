package com.example.nexusapp.models

import androidx.compose.ui.graphics.Color

data class TeamResponse(
    val id:Int,
    val name:String,
    val color: String = "0xffffffff"
)
