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
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class CheckInVM @Inject constructor(
    private val repo: Repository
): ViewModel()  {

    var participant by mutableStateOf<Resource<ParticipantResponse>>(
        Resource.Failed("Scan the QR Code")
    )

    fun checkIn(id:Int){
        viewModelScope.launch {
            repo.checkIn(id).collect{
                participant=it
            }
        }
    }
}