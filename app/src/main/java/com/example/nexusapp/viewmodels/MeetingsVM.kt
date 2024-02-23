package com.example.nexusapp.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.integration.compose.RequestState
import com.example.nexusapp.Repo.Repository
import com.example.nexusapp.Repo.Resource
import com.example.nexusapp.models.HomePageResponse.HomePageResponse
import com.example.nexusapp.models.MeetingResponse
import com.example.nexusapp.models.MemberResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MeetingsVM @Inject constructor(
    private val repo: Repository
) : ViewModel() {

  var meetings by mutableStateOf<Resource<List<MeetingResponse>>>(Resource.Loading())

    fun getAllMeetings(){
        viewModelScope.launch {
            repo.getManagerMeetings().collect{
                meetings=it
            }
        }
    }

    fun addMeeting(meetingResponse: MeetingResponse):Resource<MeetingResponse>{
        var result by  mutableStateOf<Resource<MeetingResponse>>(Resource.Loading())
        viewModelScope.launch {
          repo.addMeeting(meetingResponse).collect{
              result=it
          }
        }
        return result
    }

    fun updateMeeting(meetingResponse: MeetingResponse,team_id:Int):Resource<MeetingResponse>{
        var result by  mutableStateOf<Resource<MeetingResponse>>(Resource.Loading())
        viewModelScope.launch {
            repo.updateMeeting(meetingResponse,team_id).collect{
                result=it
            }
        }
        return result
    }

    fun deleteMeeting(id:Int):Resource<String>{
        var result by  mutableStateOf<Resource<String>>(Resource.Loading())
        viewModelScope.launch {
            repo.deleteMeeting(id).collect{
                result=it
            }
        }
        return result
    }


    init {
        getAllMeetings()
    }

}