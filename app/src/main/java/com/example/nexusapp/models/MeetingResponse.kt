package com.example.nexusapp.models

data class MeetingResponse(
    val id:Int,
    var title:String,
    var date:String,
    var time:String="",
    var team_id : Int,
    val color:Long=0xffffffff,
    var description : String
)
