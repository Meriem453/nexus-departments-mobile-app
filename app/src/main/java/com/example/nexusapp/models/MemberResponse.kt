package com.example.nexusapp.models

data class MemberResponse(
    val id:Int,
    val name:String,
    val points : Int,
    val team:String,
    val email:String,
    val password:String,
    val department_id:Int
)
