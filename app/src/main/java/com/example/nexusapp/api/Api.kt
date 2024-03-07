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
import kotlinx.coroutines.flow.Flow
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
    @FormUrlEncoded
    @POST("user/departmentOverview")
    suspend fun homePage(
        @Field("token") token:String
    ):HomePageResponse

    //Members//////////////////////////////////////////////////////////////////////////////
    @FormUrlEncoded
    @POST("member/departmentMembers")
    suspend fun membersList(
        @Field("token") token:String
    ): List<MemberResponse>

    @FormUrlEncoded
    @POST("member/register")
    suspend fun addMember(
        @Field("name") name:String,
        @Field("email") email:String,
        @Field("password") password:String,
        @Field("points") points:Int,
        @Field("team_id") team_id:Int,
        @Field("token") token:String,
    ):MemberResponse

    @FormUrlEncoded
    @PUT("member/update")
    suspend fun editMember(
        @Field("name") name:String,
        //@Field("email") email:String,
        //@Field("password") password:String,
        @Field("points") points:Int,
        @Field("team_id") team_id:Int,
        //@Field("department_id") department_id:Int,
        @Field("id") id:Int,
        @Field("token") token:String,
    )

    @FormUrlEncoded
    @POST("member/delete")
    suspend fun deleteMember(
        @Field("id") id:Int,
        @Field("token") token:String
    )
    //Meetings///////////////////////////////////////////////////////////////////////////////////////
    @FormUrlEncoded
    @POST("meeting/manager")
    suspend fun meetingsList(
        @Field("token") token:String
    ):List<MeetingResponse>

    @FormUrlEncoded
    @POST("meeting/create")
    suspend fun createMeeting(
        @Field("title") title:String,
        @Field("date") date:String,
        @Field("time") time:String,
        @Field("team_id") team_id:Int,
        @Field("description") description:String,
        @Field("token") token:String
    ):MeetingResponse

    @FormUrlEncoded
    @PUT("meeting/update")
    suspend fun updateMeeting(
        @Field("id") id:Int,
        @Field("title") title:String,
        @Field("date") date:String,
        @Field("team_id") team_id:Int,
        @Field("description") description:String,
        @Field("token") token:String
    )

    @FormUrlEncoded
    @POST("meeting/delete")
    suspend fun deleteMeeting(
        @Field("id") id:Int,
        @Field("token") token:String
    )

    //Projects//////////////////////////////////////////////////////////////////
    @FormUrlEncoded
    @POST("project/manager")
    suspend fun projectsList(
        @Field("token") token:String
    ):List<ProjectResponse>

    @FormUrlEncoded
    @POST("project/addProject")
    suspend fun addProject(
        @Field("title") title:String,
        @Field("progress") progress:Int,
        @Field("token") token: String
    ): Project

    @FormUrlEncoded
    @PUT("project/update")
    suspend fun updateProject(
        @Field("id") id: Int,
        @Field("title") title:String,
        @Field("progress") progress:Int,
        @Field("token") token:String
    )

    @FormUrlEncoded
    @POST("project/delete")
    suspend fun deleteProject(
        @Field("id") id:Int,
        @Field("token") token:String
    )

    //Events/////////////////////////////////////////////////////////////////////
    @FormUrlEncoded
    @POST("event/addEvent")
    suspend fun addEvent(
        @Field("name") name:String,
        @Field("date") date: String,
        @Field("details") details: String,
        @Field("token") token:String
    ):EventResponse

    @FormUrlEncoded
    @POST("event/all")
    suspend fun eventsList(
        @Field("token") token:String
    ):List<EventResponse>

    @FormUrlEncoded
    @POST("event/nextEvents")
    suspend fun nextEvents(
        @Field("token") token:String
    ):List<EventResponse>

    @FormUrlEncoded
    @POST("event/next")
    suspend fun nextEvent(
        @Field("token") token:String
    ):List<EventResponse>

    @FormUrlEncoded
    @POST("event/delete")
   suspend fun deleteEvent(
       @Field("id") id:Int,
       @Field("token") token:String
   )
    //Tasks/////////////////////////////////////////////////////////////////////////////////////////
    @FormUrlEncoded
    @POST("task/all")
    suspend fun getAllTasks(
        @Field("token") token:String,
        @Field("project_id") project_id:Int,

    ):List<TaskResponse>

    @FormUrlEncoded
    @POST("task/create")
    suspend fun createTask(
        @Field("title") title:String,
        @Field("description") description:String,
        @Field("progress") progress:Int,
        @Field("deadline") deadline:String,
        @Field("team_id") team_id:Int,
        @Field("project_id") project_id:Int,
        @Field("status") status:String,
        @Field("token") token:String
    ):TaskResponse
    @FormUrlEncoded
    @PUT("task/update")
    suspend fun updateTask(
        @Field("id") id:Int,
        @Field("title") title:String,
        @Field("description") description:String,
        @Field("progress") progress:Int,
        @Field("deadline") deadline:String,
        @Field("team_id") team_id:Int,
        @Field("project_id") project_id:Int,
        @Field("status") status:String,
        @Field("token") token:String
    )
    @FormUrlEncoded
    @POST("task/delete")
    suspend fun deleteTask(
        @Field("id") id:Int,
        @Field("token") token:String
    )

//Teams///////////////////////////////////////////////////////////////////////
@FormUrlEncoded
    @POST("team/all")
    suspend fun getAllTeams(
    @Field("token") token:String
    ):List<TeamResponse>


    @FormUrlEncoded
    @POST("team/create")
    suspend fun createTeam(
        @Field("name") name: String,
        @Field("color") color:String,
        @Field("token") token:String
    ):TeamResponse

    @FormUrlEncoded
    @PUT("team/update")
    suspend fun updateTeam(
        @Field("id") id:Int,
        @Field("name") name: String,
        @Field("color") color:String,
        @Field("token") token:String
    )

    @FormUrlEncoded
    @POST("team/delete")
    suspend fun deleteTeam(
        @Field("id") id: Int,
        @Field("token") token:String
    )

    //CheckIn////////////////////////////////////////
    @FormUrlEncoded
    @PUT("participant/scan")
    suspend fun checkIn(
        @Body id:participantId
    ):ParticipantResponse

    @FormUrlEncoded
    @POST("participant/event")
    suspend fun getAllParticipants(
        @Field("event_id") event_id:Int,
        @Field("token") token:String
    ):List<ParticipantResponse>


}