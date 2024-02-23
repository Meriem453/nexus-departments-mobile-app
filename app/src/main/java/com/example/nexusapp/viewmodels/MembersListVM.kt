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
import com.example.nexusapp.models.MemberResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MembersListVM @Inject constructor(
    private val repo: Repository
) : ViewModel() {

    var membersList by mutableStateOf<Resource<List<MemberResponse>>>(Resource.Loading())


    fun addMember(memberResponse: MemberResponse,team_id: Int):Resource<MemberResponse>{
        var result by  mutableStateOf<Resource<MemberResponse>>(Resource.Loading())
        viewModelScope.launch {
            repo.addMember(memberResponse,team_id).collect{
                result=it
            }
        }
        return result
    }

    fun updateMember(memberResponse: MemberResponse, team_id:Int):Resource<MemberResponse>{
        var result by  mutableStateOf<Resource<MemberResponse>>(Resource.Loading())
        viewModelScope.launch {
            repo.editMember(memberResponse,team_id).collect{
                result=it
            }
        }
        return result
    }

    fun deleteMember(id:Int):Resource<String>{
        var result by  mutableStateOf<Resource<String>>(Resource.Loading())
        viewModelScope.launch {
            repo.deleteMember(id).collect{
                result=it
            }
        }
        return result
    }


    init {

        viewModelScope.launch {
           repo.getMembersList().collect{
                membersList = it
            }

        }


    }


}