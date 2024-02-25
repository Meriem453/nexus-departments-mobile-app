package com.example.nexusapp.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nexusapp.Repo.Repository
import com.example.nexusapp.Repo.Resource
import com.example.nexusapp.models.EventResponse
import com.example.nexusapp.models.HomePageResponse.Event
import com.example.nexusapp.models.MeetingResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import retrofit2.Call
import javax.inject.Inject

@HiltViewModel
class EventsVM @Inject constructor(
    private val repo: Repository
):ViewModel() {

    var eventsList by mutableStateOf<Resource<List<EventResponse>>>(Resource.Loading())
    var eventDelete by mutableStateOf<Resource<String>>(Resource.Loading())
    var event by mutableStateOf<Resource<EventResponse>>(Resource.Loading())
    fun getEventsList() {
        viewModelScope.launch {
            repo.getEvents().collect {
                eventsList = it

            }
        }
    }

    suspend fun addEvent(eventResponse: EventResponse): Flow<Resource<EventResponse>> {
        return repo.addEvent(eventResponse)
    }

    fun deleteEvent(id: Int) {
        viewModelScope.launch {
            repo.deleteEvent(id).collect {
                eventDelete = it

            }
        }
    }

    init {
        getEventsList()
    }
}