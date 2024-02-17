package com.example.nexusapp.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nexusapp.Repo.Repository
import com.example.nexusapp.Repo.Resource
import com.example.nexusapp.models.HomePageResponse.HomePageResponse
import com.example.nexusapp.models.MeetingResponse
import com.example.nexusapp.models.MemberResponse
import com.example.nexusapp.models.ProjectResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProjectsVM @Inject constructor(
    private val repo: Repository
) : ViewModel() {

    private val _state = MutableStateFlow(emptyList<ProjectResponse>())
    val state: StateFlow<List<ProjectResponse>>
        get() = _state


    init {

        viewModelScope.launch {
            val projects = repo.getManagerProjects()
            _state.value = projects
        }


    }
}
