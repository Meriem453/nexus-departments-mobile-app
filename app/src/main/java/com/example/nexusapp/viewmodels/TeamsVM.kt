package com.example.nexusapp.viewmodels

import android.app.Application
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nexusapp.Repo.Repository
import com.example.nexusapp.Repo.Resource
import com.example.nexusapp.models.TeamResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TeamsVM @Inject constructor(
    private val repo: Repository,
    private val context:Application
) : ViewModel() {

    var teams by mutableStateOf<Resource<List<TeamResponse>>>(Resource.Loading())

    fun getAllTeams(){
        viewModelScope.launch {
            repo.getAllTeams().collect{
                teams=it
            }
        }
    }

    fun addTeam(teamResponse: TeamResponse) {

        viewModelScope.launch {
            repo.addTeam(teamResponse).collect{
                if(it !is Resource.Loading)
                    Toast.makeText(context,it.message, Toast.LENGTH_LONG).show()
            }
        }
        getAllTeams()
    }

    fun updateTeam(teamResponse: TeamResponse){

        viewModelScope.launch {
            repo.updateTeam(teamResponse).collect{
                if(it !is Resource.Loading)
                    Toast.makeText(context,it.message, Toast.LENGTH_LONG).show()
            }
        }
        getAllTeams()
    }

    fun deleteTeam(id:Int){

        viewModelScope.launch {
            repo.deleteTeam(id).collect{
                if(it !is Resource.Loading)
                    Toast.makeText(context,it.message, Toast.LENGTH_LONG).show()
            }
        }
        getAllTeams()
    }


    init {
        getAllTeams()
    }
}