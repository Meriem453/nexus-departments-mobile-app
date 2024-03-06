package com.example.nexusapp.models

data class TaskResponse(
    val id : Int,
    val project_id:Int,
    var team_id:Int,
    var description:String,
    var status:String,
    var title:String,
    var progress:Int,
    var deadline:String,
    var team_color:String,
    var team_name:String

)
