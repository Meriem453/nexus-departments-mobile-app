package com.example.nexusapp.api

import com.example.nexusapp.models.EventResponse
import com.example.nexusapp.models.HomePageResponse.HomePageResponse
import com.example.nexusapp.models.HomePageResponse.Project
import com.example.nexusapp.models.LoginResponse
import com.example.nexusapp.models.MeetingResponse
import com.example.nexusapp.models.MemberResponse
import com.example.nexusapp.models.ProjectResponse
import com.example.nexusapp.models.TaskResponse
import com.example.nexusapp.screens.TasksResponse
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface Api {
//Manager
    @FormUrlEncoded
    @POST("user/login")
    fun userLogin(
        @Field("email") email:String,
        @Field("password") password: String
    ): Call<LoginResponse>

    @GET("user/departmentOverview/{token}")
    suspend fun homePage(
        @Path("token") token:String
    ):HomePageResponse

    //Members
    @GET("member/departmentMembers/{token}")
    suspend fun membersList(
        @Path("token") token:String
    ):List<MemberResponse>

    @POST("member/register")
    suspend fun addMember(
        @Field("name") name:String,
        @Field("email") email:String,
        @Field("password") password:String,
        @Field("points") points:String,
        @Field("team_id") team_id:String,
        @Field("department_id") department_id:String
    ):MemberResponse

    @PUT("member/{id}")
    suspend fun editMember(
        @Field("name") name:String,
        @Field("email") email:String,
        @Field("password") password:String,
        @Field("points") points:String,
        @Field("team_id") team_id:String,
        @Field("department_id") department_id:String,
        @Path("id") id:Int
    ):MemberResponse

    @DELETE("member/{id}")
    suspend fun deleteMember(
        @Path("id") id:Int
    )
    //Meetings
    @GET("meeting/manager/{token}")
    suspend fun meetingsList(
        @Path("token") token:String
    ):List<MeetingResponse>

    @POST("meeting/create")
    suspend fun createMeeting(
        @Field("title") title:String,
        @Field("date") date:String,
        @Field("team_id") team_id:Int,
        @Field("description") description:String
    ):MeetingResponse
    //TODO("update meeting")
    @DELETE("meeting/delete/{id}")
    suspend fun deleteMeeting(
        @Path("id") id:Int
    )

    //Projects
    @GET("project/manager/{token}")
    suspend fun projectsList(
        @Path("token") token:String
    ):List<ProjectResponse>

    @FormUrlEncoded
    @POST("project/addProject")
    suspend fun addProject(
        @Field("title") title:String,
        @Field("user_id") user_id: Int,
        @Field("progress") progress:Int
    ): Project

    @FormUrlEncoded
    @PUT("project/{id}")
    suspend fun updateProject(
        @Field("title") title:String,
        @Field("user_id") user_id: Int,
        @Field("progress") progress:Int,
        @Path("id") id:Int
    ): Project

    @DELETE("project/{id}")
    suspend fun deleteProject(
        @Path("id") id:Int
    )

    //Events
    @FormUrlEncoded
    @POST("event/addEvent")
    fun addEvent(
        @Field("name") name:String,
        @Field("date") date: String
    ): Call<EventResponse>

    @GET("event/all")
    suspend fun eventsList():List<EventResponse>

    @GET("event/nextEvents")
    suspend fun nextEvents():List<EventResponse>

    @GET("event/next")
    suspend fun nextEvent():List<EventResponse>

    //Tasks
    @GET("tasks/all")
    suspend fun getAllTasks():List<TaskResponse>

    @POST("tasks/create")
    suspend fun createTask(
        @Field("title") title:String,
        @Field("description") description:String,
        @Field("progress") progress:Int,
        @Field("deadline") deadline:String,
        @Field("team_id") team_id:Int,
        @Field("project_id") project_id:String,
    )
    //TODO("update task ")
    @DELETE("tasks/delete/{id}")
    suspend fun deleteTask(
        @Path("id") id:Int
    )

}