package com.example.nexusapp.Repo

import android.util.Log
import com.bumptech.glide.integration.compose.RequestState
import com.example.nexusapp.api.Api
import com.example.nexusapp.models.EventResponse
import com.example.nexusapp.models.HomePageResponse.HomePageResponse
import com.example.nexusapp.models.HomePageResponse.Project
import com.example.nexusapp.models.MeetingResponse
import com.example.nexusapp.models.MemberResponse
import com.example.nexusapp.models.ParticipantResponse
import com.example.nexusapp.models.ProjectResponse
import com.example.nexusapp.models.Requests.participantId
import com.example.nexusapp.models.TaskResponse
import com.example.nexusapp.models.TeamResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Call
import javax.inject.Inject

class Repository @Inject constructor(
    private val api: Api,
    private val token:String?
    ) {
    //user
    suspend fun getHomePageInfo(): Flow<Resource<HomePageResponse>> = flow {
        emit(Resource.Loading())
        try {
            val data = api.homePage(token!!)
            if (data != null) {
                emit(Resource.Success(data, "Success fetching data"))
                Log.d("data", data.toString())
            } else emit(Resource.Failed("there is no data"))

        } catch (e: Exception) {
            emit(Resource.Failed(e.localizedMessage))
        }
    }

    //members

    suspend fun getMembersList(): Flow<Resource<List<MemberResponse>>> {
        return flow {
            emit(Resource.Loading())
            try {
                val members=api.membersList(token!!)
                emit(Resource.Success(members,"Success fetching members"))
            } catch (e: Exception) {
                emit(Resource.Failed(e.localizedMessage))
            }
        }

    }
    suspend fun addMember(memberResponse: MemberResponse,team_id:Int):Flow<Resource<MemberResponse>>{
        return flow<Resource<MemberResponse>> {
            emit(Resource.Loading())
            try {
                val member = api.addMember(
                    memberResponse.name,
                    memberResponse.email,
                    memberResponse.password,
                    memberResponse.points,
                    team_id,
                    memberResponse.department_id

                )
                emit(Resource.Success(member,"Success adding member"))
            }catch (e:Exception){
                emit(Resource.Failed(e.localizedMessage))
            }
        }
    }

    suspend fun editMember(memberResponse: MemberResponse,team_id:Int):Flow<Resource<MemberResponse>>{
        return flow<Resource<MemberResponse>> {
            emit(Resource.Loading())
            try {
                val member = api.editMember(
                    memberResponse.name,
                    memberResponse.email,
                    memberResponse.password,
                    memberResponse.points,
                    team_id,
                    memberResponse.department_id,
                    memberResponse.id

                )
                emit(Resource.Success(member,"Success editing member"))
            }catch (e:Exception){
                emit(Resource.Failed(e.localizedMessage))
            }
        }
    }
    suspend fun deleteMember(member_id:Int):Flow<Resource<String>>{
        return flow<Resource<String>> {
            emit(Resource.Loading())
            try {
                api.deleteMember(member_id)
                emit(Resource.Success("","Success deleting member"))
            }catch (e:Exception){
                emit(Resource.Failed(e.localizedMessage))
            }
        }
    }
    //meetings
    suspend fun addMeeting(meetingResponse: MeetingResponse):Flow<Resource<MeetingResponse>>{
        return flow<Resource<MeetingResponse>> {
            emit(Resource.Loading())
            try {
                val meeting=api.createMeeting(
                    meetingResponse.title,
                    meetingResponse.date,
                    meetingResponse.team_id,
                    meetingResponse.description
                )
                emit(Resource.Success(meeting,"Success adding meeting"))
            }catch (e:Exception){
                emit(Resource.Failed(e.localizedMessage))
            }
        }
    }
    suspend fun updateMeeting(meetingResponse: MeetingResponse,team_id: Int):Flow<Resource<MeetingResponse>>{
        return flow {
            emit(Resource.Loading())
            try {
                val meeting=api.updateMeeting(
                    meetingResponse.id,
                    meetingResponse.title,
                    meetingResponse.date,
                    team_id,
                    meetingResponse.description,

                )
                emit(Resource.Success(meeting,"success updating meeting"))
            }catch (e:Exception){
                emit(Resource.Failed(e.localizedMessage))
            }
        }
    }
    suspend fun deleteMeeting(meet_id:Int):Flow<Resource<String>>{
        return flow<Resource<String>> {
            emit(Resource.Loading())
            try {
                api.deleteMeeting(meet_id)
                emit(Resource.Success("","Success deleting meeting"))
            }catch (e:Exception){
                emit(Resource.Failed(e.localizedMessage))
            }
        }
    }
    suspend fun getManagerMeetings(): Flow<Resource<List<MeetingResponse>>>{
        return flow {
            emit(Resource.Loading())
            try {
                val meetings=api.meetingsList(token!!)
                emit(Resource.Success(meetings,"Success fetching meetings"))

            } catch (e: Exception) {
                emit(Resource.Failed(e.localizedMessage))
            }
        }
    }
//projects
    suspend fun getManagerProjects(): Flow<Resource<List<ProjectResponse>>> {
        return flow {
            emit(Resource.Loading())
            try {
            val projects = api.projectsList(token!!)
                emit(Resource.Success(projects,"Success fetching projects"))
        } catch (e: Exception) {
            emit(Resource.Failed(e.localizedMessage))
        }
      }
    }

    suspend fun addProject(projectResponse: Project):Flow<Resource<Project>>{
        return flow<Resource<Project>> {
            emit(Resource.Loading())
            try {
                val project=api.addProject(
                    projectResponse.title,
                    projectResponse.progress,
                    token!!
                )
                emit(Resource.Success(project,"Success adding project"))
            }catch (e:Exception){
                emit(Resource.Failed(e.localizedMessage))
            }
        }
    }

    suspend fun updateProject(projectResponse: Project):Flow<Resource<Project>>{
        return flow<Resource<Project>> {
            emit(Resource.Loading())
            try {
                val project=api.updateProject(
                    projectResponse.id,
                    projectResponse.title,
                    projectResponse.progress
                )
                emit(Resource.Success(project,"Succesc updating project"))
            }catch (e:Exception){
                emit(Resource.Failed(e.localizedMessage))
            }
        }
    }

    suspend fun deleteProject(id:Int):Flow<Resource<String>>{
        return flow {
            emit(Resource.Loading())
            try {
                api.deleteProject(id)
                emit(Resource.Success("","project deleted"))
            }catch (e:Exception){
                emit(Resource.Failed(e.localizedMessage))
            }
        }
    }
//events
    suspend fun getEvents(): Flow<Resource<List<EventResponse>>> {
        return flow {
            emit(Resource.Loading())
            try {
                val events = api.eventsList()
                emit(Resource.Success(events,"Success fetching events"))
            } catch (e: Exception) {
                emit(Resource.Failed(e.localizedMessage))
            }
        }
    }
    suspend fun addEvent(name: String, date: String, details: String): Flow<Resource<EventResponse>> {
        return flow {
            emit(Resource.Loading())
            try {
                val event = api.addEvent(name, date, details)
                emit(Resource.Success(event, "Success fetching events"))
            } catch (e: Exception) {
                emit(Resource.Failed(e.localizedMessage))
            }
        }
    }
    suspend fun deleteEvent(id:Int):Flow<Resource<String>>{
        return flow {
            emit(Resource.Loading())
            try {
                api.deleteEvent(id)
                emit(Resource.Success("","event deleted"))
            }catch (e:Exception){
                emit(Resource.Failed(e.localizedMessage))
            }
        }
    }

    //Tasks
    suspend fun getAllTasks():Flow<Resource<List<TaskResponse>>>{
        return flow {
            emit(Resource.Loading())
            try {
                val tasks=api.getAllTasks()
                emit(Resource.Success(tasks,"Success fetching tasks"))
            }catch (e:Exception){
                emit(Resource.Failed(e.localizedMessage))
            }
        }
    }

    suspend fun updateTask(taskResponse: TaskResponse):Flow<Resource<TaskResponse>>{
        return flow {
            emit(Resource.Loading())
            try {
                val task=api.updateTask(
                    taskResponse.id,
                    taskResponse.title,
                    taskResponse.description,
                    taskResponse.progress,
                    taskResponse.deadline,
                    taskResponse.team_id,
                    taskResponse.project_id
                )
                emit(Resource.Success(task,"Success updating tasks"))
            }catch (e:Exception){
                emit(Resource.Failed(e.localizedMessage))
            }
        }
    }
    suspend fun addTask(taskResponse: TaskResponse):Flow<Resource<TaskResponse>>{
        return flow {
            emit(Resource.Loading())
            try {
                val task=api.createTask(
                    taskResponse.title,
                    taskResponse.description,
                    taskResponse.progress,
                    taskResponse.deadline,
                    taskResponse.team_id,
                    taskResponse.project_id
                )
                emit(Resource.Success(task,"Success creating task"))
            }catch (e:Exception){
                emit(Resource.Failed(e.localizedMessage))
            }
        }
    }
    suspend fun deleteTask(id:Int):Flow<Resource<String>>{
        return flow {
            emit(Resource.Loading())

            try {
                api.deleteTask(id)
                emit(Resource.Success("","Success deleting task"))
            }catch (e:Exception){
                emit(Resource.Failed(e.localizedMessage))
            }
        }
    }

//Teams

    suspend fun getAllTeams():Flow<Resource<List<TeamResponse>>>{
        return flow {
            emit(Resource.Loading())
            try {
                val teams = api.getAllTeams()
                emit(Resource.Success(teams,"Success fetching teams"))
            }catch (e:Exception){
                emit(Resource.Failed(e.localizedMessage))
            }
        }
    }
    suspend fun addTeam(teamResponse: TeamResponse):Flow<Resource<TeamResponse>>{
        return flow {
            emit(Resource.Loading())
            try {
                val team=api.createTeam(
                    teamResponse.name,
                    teamResponse.color
                )
                emit(Resource.Success(team,"Success adding team"))
            }catch (e:Exception){
                emit(Resource.Failed(e.localizedMessage))
            }
        }
    }
    suspend fun updateTeam(teamResponse: TeamResponse):Flow<Resource<TeamResponse>>{
        return flow {
            emit(Resource.Loading())
            try {
                val team=api.updateTeam(
                    teamResponse.id,
                    teamResponse.name,
                    teamResponse.color
                )
                emit(Resource.Success(team,"Success updating team"))
            }catch (e:Exception){
                emit(Resource.Failed(e.localizedMessage))
            }
        }
    }

    suspend fun deleteTeam(id: Int):Flow<Resource<String>>{
        return flow {
            emit(Resource.Loading())
            try {
                api.deleteTeam(id)
                emit(Resource.Success("","Success deleting team"))
            }catch (e:Exception){
                emit(Resource.Failed(e.localizedMessage))
            }
        }
    }
    //Check In
    suspend fun checkIn(id:Int):Flow<Resource<ParticipantResponse>>{
        return flow {
            emit(Resource.Loading())
            try {
                val participant=api.checkIn(participantId(id))
                emit(Resource.Success(participant,"Success validating participant"))
            }catch (e:Exception){
                emit(Resource.Failed(e.localizedMessage))
            }
        }
    }
}
