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
import com.example.nexusapp.models.MemberResponse
import com.example.nexusapp.models.TeamResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MembersListVM @Inject constructor(
    private val repo: Repository,
    private val context:Application
) : ViewModel() {

    var membersList by mutableStateOf<Resource<List<MemberResponse>>>(Resource.Loading())
    var teams by mutableStateOf<Resource<List<TeamResponse>>>(Resource.Loading())

fun getAllMembers(){
    viewModelScope.launch {
        repo.getMembersList().collect{
            membersList = it
        }
    }
}

    fun addMember(memberResponse: MemberResponse,team_id: Int){

        viewModelScope.launch {

            repo.addMember(memberResponse,team_id).collect{
                if(it !is Resource.Loading){
                    Toast.makeText(context,it.message,Toast.LENGTH_LONG).show()
                getAllMembers()}
            }
        }

    }

    fun updateMember(memberResponse: MemberResponse, team_id:Int){

        viewModelScope.launch {
            repo.editMember(memberResponse,team_id).collect{
                if(it !is Resource.Loading){
                    Toast.makeText(context,it.message,Toast.LENGTH_LONG).show()
                getAllMembers()}
            }
        }

    }

    fun deleteMember(id:Int){

        viewModelScope.launch {
            repo.deleteMember(id).collect{
                if(it !is Resource.Loading){
                    Toast.makeText(context,it.message,Toast.LENGTH_LONG).show()
                getAllMembers()}
            }
        }


    }


    init {
        getAllMembers()
        viewModelScope.launch {

            repo.getAllTeams().collect{
                teams=it
            }
        }
    }
}