package com.example.nexusapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nexusapp.Repo.Repository
import com.example.nexusapp.models.EventResponse
import com.example.nexusapp.models.MemberResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventsVM @Inject constructor(
    private val repo: Repository
) : ViewModel() {

    private val _state = MutableStateFlow(emptyList<EventResponse>())
    val state: StateFlow<List<EventResponse>>
        get() = _state


    init {

        viewModelScope.launch {
            val events = repo.getEvents()
            _state.value =events
        }


    }


}