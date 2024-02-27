package com.example.nexusapp.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nexusapp.Repo.Repository
import com.example.nexusapp.Repo.Resource
import com.example.nexusapp.models.ParticipantResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ParticipantsVM @Inject constructor(
    private val repo: Repository
) : ViewModel() {
    var participants by mutableStateOf<Resource<List<ParticipantResponse>>>(Resource.Loading())
    fun getAllPariticipants(event_id:Int){
        viewModelScope.launch {
            repo.getAllParticipants(event_id).collect{
                participants=it
            }
        }

    }

}