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
import com.example.nexusapp.models.HomePageResponse.Project
import com.example.nexusapp.models.ProjectResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProjectsVM @Inject constructor(
    private val repo: Repository,
    private val context:Application
) : ViewModel() {

    var projects by mutableStateOf<Resource<List<ProjectResponse>>>(Resource.Loading())

    fun getAllProjects(){
        viewModelScope.launch {
            repo.getManagerProjects().collect{
                projects=it
            }
        }
    }

    fun addProject(project: Project){

        viewModelScope.launch {
            repo.addProject(project).collect{
                if(it !is Resource.Loading)
                    Toast.makeText(context,it.message, Toast.LENGTH_LONG).show()
            }
        }
    }

    fun updateProject(project: Project) {

        viewModelScope.launch {
            repo.updateProject(project).collect{
                if(it !is Resource.Loading)
                    Toast.makeText(context,it.message, Toast.LENGTH_LONG).show()
            }
        }

    }

    fun deleteProject(id:Int) {

        viewModelScope.launch {
            repo.deleteProject(id).collect{
                if(it !is Resource.Loading)
                    Toast.makeText(context,it.message, Toast.LENGTH_LONG).show()
            }
        }
    }


    init {
        getAllProjects()
    }
}
