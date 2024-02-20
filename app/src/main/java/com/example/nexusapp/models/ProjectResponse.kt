package com.example.nexusapp.models

import android.os.Parcelable

@kotlinx.parcelize.Parcelize
data class ProjectResponse(
    val id : Int,
    val title : String,
    val pending_tasks : String,
    val total_tasks : String,
    val completed_tasks : String,

    ): Parcelable
