package com.example.nexusapp.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nexusapp.Repo.Repository
import com.example.nexusapp.Repo.Resource
import com.example.nexusapp.models.TeamResponse
import kotlinx.coroutines.launch
import javax.inject.Inject

class TeamsVM @Inject constructor(
    private val repo: Repository
) : ViewModel()  {

    var teams by mutableStateOf<Resource<List<TeamResponse>>>(Resource.Loading())

    fun getAllTeams(){
        viewModelScope.launch {
            repo.getAllTeams().collect{
                teams=it
            }
        }
    }

    fun addTeam(teamResponse: TeamResponse): Resource<TeamResponse> {
        var result by  mutableStateOf<Resource<TeamResponse>>(Resource.Loading())
        viewModelScope.launch {
            repo.addTeam(teamResponse).collect{
                result=it
            }
        }
        return result
    }

    fun updateTeam(teamResponse: TeamResponse): Resource<TeamResponse> {
        var result by  mutableStateOf<Resource<TeamResponse>>(Resource.Loading())
        viewModelScope.launch {
            repo.updateTeam(teamResponse).collect{
                result=it
            }
        }
        return result
    }

    fun deleteTeam(id:Int): Resource<String> {
        var result by  mutableStateOf<Resource<String>>(Resource.Loading())
        viewModelScope.launch {
            repo.deleteTeam(id).collect{
                result=it
            }
        }
        return result
    }


    init {
        getAllTeams()
    }
}