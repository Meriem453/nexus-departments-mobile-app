package com.example.nexusapp.models.HomePageResponse

data class HomePageResponse(
    val departmentName: String,
    val done_projects: Int,
    val event: Event,
    val members_number: Int,
    val pending_projects: Int,
    val project: Project,
    val userData: UserData
)