package com.example.nexusapp.api

import com.example.nexusapp.models.EventResponse
import com.example.nexusapp.models.HomePageResponse.HomePageResponse
import com.example.nexusapp.models.HomePageResponse.Project
import com.example.nexusapp.models.LoginResponse
import com.example.nexusapp.models.MeetingResponse
import com.example.nexusapp.models.MemberResponse
import com.example.nexusapp.models.ParticipantResponse
import com.example.nexusapp.models.ProjectResponse
import com.example.nexusapp.models.Requests.participantId
import com.example.nexusapp.models.TaskResponse
import com.example.nexusapp.models.TeamResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface Api {
//Manager///////////////////////////////////////////////////////////////
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

    //Members//////////////////////////////////////////////////////////////////////////////
    @GET("member/departmentMembers/{token}")
    suspend fun membersList(
        @Path("token") token:String
    ):List<MemberResponse>

    @FormUrlEncoded
    @POST("member/register")
    suspend fun addMember(
        @Field("name") name:String,
        @Field("email") email:String,
        @Field("password") password:String,
        @Field("points") points:Int,
        @Field("team_id") team_id:Int,
        @Field("department_id") department_id:Int
    ):MemberResponse

    @FormUrlEncoded
    @PUT("member/{id}")
    suspend fun editMember(
        @Field("name") name:String,
        @Field("email") email:String,
        @Field("password") password:String,
        @Field("points") points:Int,
        @Field("team_id") team_id:Int,
        @Field("department_id") department_id:Int,
        @Path("id") id:Int
    ):MemberResponse

    @DELETE("member/{id}")
    suspend fun deleteMember(
        @Path("id") id:Int
    )
    //Meetings///////////////////////////////////////////////////////////////////////////////////////
    @GET("meeting/manager/{token}")
    suspend fun meetingsList(
        @Path("token") token:String
    ):List<MeetingResponse>

    @FormUrlEncoded
    @POST("meeting/create")
    suspend fun createMeeting(
        @Field("title") title:String,
        @Field("date") date:String,
        @Field("team_id") team_id:Int,
        @Field("description") description:String
    ):MeetingResponse

    @FormUrlEncoded
    @PUT("meeting/update")
    suspend fun updateMeeting(
        @Field("id") id:Int,
        @Field("title") title:String,
        @Field("date") date:String,
        @Field("team_id") team_id:Int,
        @Field("description") description:String,
    ):MeetingResponse

    @DELETE("meeting/delete/{id}")
    suspend fun deleteMeeting(
        @Path("id") id:Int
    )

    //Projects//////////////////////////////////////////////////////////////////
    @GET("project/manager/{token}")
    suspend fun projectsList(
        @Path("token") token:String
    ):List<ProjectResponse>

    @FormUrlEncoded
    @POST("project/addProject/{token}")
    suspend fun addProject(
        @Field("title") title:String,
        @Field("progress") progress:Int,
        @Path("token") token: String
    ): Project

    @FormUrlEncoded
    @PUT("project/update")
    suspend fun updateProject(
        @Field("id") id: Int,
        @Field("title") title:String,
        @Field("progress") progress:Int,
    ): Project

    @DELETE("project/delete/{id}")
    suspend fun deleteProject(
        @Path("id") id:Int
    )

    //Events/////////////////////////////////////////////////////////////////////
    @FormUrlEncoded
    @POST("event/addEvent")
    suspend fun addEvent(
        @Field("name") name:String,
        @Field("date") date: String,
        @Field("details") details: String
    ):EventResponse

    @GET("event/all")
    suspend fun eventsList():List<EventResponse>

    @GET("event/nextEvents")
    suspend fun nextEvents():List<EventResponse>

    @GET("event/next")
    suspend fun nextEvent():List<EventResponse>

    @DELETE("event/delete/{id}")
   suspend fun deleteEvent(
       @Path("id") id:Int
   )
    //Tasks/////////////////////////////////////////////////////////////////////////////////////////
    @GET("tasks/all")
    suspend fun getAllTasks():List<TaskResponse>

    @FormUrlEncoded
    @POST("tasks/create")
    suspend fun createTask(
        @Field("title") title:String,
        @Field("description") description:String,
        @Field("progress") progress:Int,
        @Field("deadline") deadline:String,
        @Field("team_id") team_id:Int,
        @Field("project_id") project_id:Int,
    ):TaskResponse
    @FormUrlEncoded
    @POST("tasks/update")
    suspend fun updateTask(
        @Field("id") id:Int,
        @Field("title") title:String,
        @Field("description") description:String,
        @Field("progress") progress:Int,
        @Field("deadline") deadline:String,
        @Field("team_id") team_id:Int,
        @Field("project_id") project_id:Int,
    ):TaskResponse
    @DELETE("tasks/delete/{id}")
    suspend fun deleteTask(
        @Path("id") id:Int
    )

//Teams///////////////////////////////////////////////////////////////////////
    @GET("team/all")
    suspend fun getAllTeams():List<TeamResponse>

    @FormUrlEncoded
    @POST("team/create")
    suspend fun createTeam(
        @Field("name") name: String,
        @Field("color") color:Long,
    ):TeamResponse

    @FormUrlEncoded
    @PUT("team/update")
    suspend fun updateTeam(
        @Field("id") id:Int,
        @Field("name") name: String,
        @Field("color") color:Long,
    ):TeamResponse

    @DELETE("team/delete/{id}")
    suspend fun deleteTeam(
        @Path("id") id: Int
    )

    //CheckIn////////////////////////////////////////
    @PUT("participant/scan")
    suspend fun checkIn(
        @Body id:participantId
    ):ParticipantResponse


}