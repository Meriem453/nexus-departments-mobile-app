package com.example.nexusapp.api

import com.example.nexusapp.models.EventResponse
import com.example.nexusapp.models.HomePageResponse.HomePageResponse
import com.example.nexusapp.models.HomePageResponse.Project
import com.example.nexusapp.models.LoginResponse
import com.example.nexusapp.models.MeetingResponse
import com.example.nexusapp.models.MemberResponse
import com.example.nexusapp.models.ProjectResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface Api {

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

    @GET("member/departmentMembers/{token}")
    suspend fun membersList(
        @Path("token") token:String
    ):List<MemberResponse>

    @GET("meeting/manager/{token}")
    suspend fun meetingsList(
        @Path("token") token:String
    ):List<MeetingResponse>

    @GET("project/manager/{token}")
    suspend fun projectsList(
        @Path("token") token:String
    ):List<ProjectResponse>

    @FormUrlEncoded
    @POST("event/addEvent")
    fun addEvent(
        @Field("name") name:String,
        @Field("date") date: String
    ): Call<EventResponse>

    @GET("event/all")
    suspend fun eventsList():List<EventResponse>

    @FormUrlEncoded
    @POST("project/addProject")
    fun addProject(
        @Field("title") title:String,
        @Field("user_id") user_id: Int,
        @Field("progress") progress:Int
    ): Call<Project>
}