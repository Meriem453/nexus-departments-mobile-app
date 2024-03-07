package com.example.nexusapp.viewmodels

import android.app.Application
import android.widget.Toast
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
import com.example.nexusapp.models.TeamResponse
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
    private val repo: Repository,
    private val context:Application
) : ViewModel() {

  var meetings by mutableStateOf<Resource<List<MeetingResponse>>>(Resource.Loading())
  var teams by mutableStateOf<Resource<List<TeamResponse>>>(Resource.Loading())

    fun getAllMeetings(){

        viewModelScope.launch {
            repo.getManagerMeetings().collect{
                meetings=it
            }
        }
    }

    fun addMeeting(meetingResponse: MeetingResponse){
        viewModelScope.launch {
          repo.addMeeting(meetingResponse).collect{
              if(it !is Resource.Loading)
                  Toast.makeText(context,it.message, Toast.LENGTH_LONG).show()
          }
        }

        getAllMeetings()
    }

    fun updateMeeting(meetingResponse: MeetingResponse,team_id:Int){
        viewModelScope.launch {
            repo.updateMeeting(meetingResponse,team_id).collect{
                if(it !is Resource.Loading)
                    Toast.makeText(context,it.message, Toast.LENGTH_LONG).show()
            }
        }
        getAllMeetings()

    }

    fun deleteMeeting(id:Int){

        viewModelScope.launch {
            repo.deleteMeeting(id).collect{
                if(it !is Resource.Loading)
                    Toast.makeText(context,it.message, Toast.LENGTH_LONG).show()
            }
        }
        getAllMeetings()
    }


    init {
        getAllMeetings()
        viewModelScope.launch {
            repo.getAllTeams().collect {
                teams = it
            }
        }
    }

}