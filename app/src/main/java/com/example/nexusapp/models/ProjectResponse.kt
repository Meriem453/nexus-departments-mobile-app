package com.example.nexusapp.models

import android.os.Parcelable
import com.example.nexusapp.models.HomePageResponse.Project

@kotlinx.parcelize.Parcelize
data class ProjectResponse(
    val id : Int,
    val title : String,
    val pending_tasks : String,
    val total_tasks : String,
    val completed_tasks : String,
    val progress:Int

    ): Parcelable
