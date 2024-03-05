package com.example.nexusapp.models

data class MemberResponse(
    val id:Int,
    val name:String,
    var points : Int,
    var team:String,
    val email:String,
    val password:String,
    val department_id:Int
)
