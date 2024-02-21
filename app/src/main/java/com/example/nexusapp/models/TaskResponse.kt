package com.example.nexusapp.models

data class TaskResponse(
    val id : Int,
    val project_id:Int,
    val description:String,
    val status:String
)
