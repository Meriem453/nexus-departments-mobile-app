package com.example.nexusapp.Repo

import android.util.Log
import com.example.nexusapp.api.Api
import com.example.nexusapp.models.EventResponse
import com.example.nexusapp.models.HomePageResponse.HomePageResponse
import com.example.nexusapp.models.MeetingResponse
import com.example.nexusapp.models.MemberResponse
import com.example.nexusapp.models.ProjectResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class Repository @Inject constructor(
    private val api: Api,
    private val token:String?
    ) {
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

    suspend fun getMembersList(): List<MemberResponse> {
        return try {
            api.membersList(token!!)
        } catch (_: Exception) {
            emptyList<MemberResponse>()
        }

    }

    suspend fun getManagerMeetings(): List<MeetingResponse> {
        return try {
            api.meetingsList(token!!)
        } catch (_: Exception) {
            emptyList<MeetingResponse>()
        }
    }

    suspend fun getManagerProjects(): List<ProjectResponse> {
        return try {
            api.projectsList(token!!)
        } catch (_: Exception) {
            emptyList<ProjectResponse>()
        }
    }

    suspend fun getEvents(): List<EventResponse> {
        return try {
            api.eventsList()
        } catch (_: Exception) {
            emptyList<EventResponse>()
        }
    }
}