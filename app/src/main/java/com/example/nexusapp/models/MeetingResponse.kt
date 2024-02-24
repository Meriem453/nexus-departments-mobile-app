package com.example.nexusapp.models

data class MeetingResponse(
    val id:Int,
    val title:String,
    val date:String,
    val time:String="",
    val team_id : Int,
    val color:Long=0xffffffff,
    val description : String
)
