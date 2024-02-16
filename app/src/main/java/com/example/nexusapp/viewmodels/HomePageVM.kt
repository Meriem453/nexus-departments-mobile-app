package com.example.nexusapp.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nexusapp.Repo.Repository
import com.example.nexusapp.Repo.Resource
import com.example.nexusapp.models.HomePageResponse.HomePageResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomePageVM @Inject constructor(
    private val repo: Repository
):ViewModel() {

    var userInfo by mutableStateOf<Resource<HomePageResponse>>(Resource.Loading())

    fun getUserInfo(){
        viewModelScope.launch {
            repo.getHomePageInfo().collect{
                 userInfo =it

            }
        }


    }
    init {
        getUserInfo()
    }

}