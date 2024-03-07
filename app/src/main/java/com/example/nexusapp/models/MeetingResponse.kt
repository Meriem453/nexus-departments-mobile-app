package com.example.nexusapp.models

import androidx.annotation.Keep

@Keep data class MeetingResponse(
    val id:Int,
    var title:String,
    var date:String,
    var time:String,
    var team_id : Int,
    val team_color:String="ffffffff",
    var description : String
)
