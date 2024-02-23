package com.example.nexusapp.viewmodels

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
    private val repo: Repository
) : ViewModel() {

    var projects by mutableStateOf<Resource<List<ProjectResponse>>>(Resource.Loading())

    fun getAllProjects(){
        viewModelScope.launch {
            repo.getManagerProjects().collect{
                projects=it
            }
        }
    }

    fun addProject(project: Project): Resource<Project> {
        var result by  mutableStateOf<Resource<Project>>(Resource.Loading())
        viewModelScope.launch {
            repo.addProject(project).collect{
                result=it
            }
        }
        return result
    }

    fun updateProject(project: Project): Resource<Project> {
        var result by  mutableStateOf<Resource<Project>>(Resource.Loading())
        viewModelScope.launch {
            repo.updateProject(project).collect{
                result=it
            }
        }
        return result
    }

    fun deleteProject(id:Int): Resource<String> {
        var result by  mutableStateOf<Resource<String>>(Resource.Loading())
        viewModelScope.launch {
            repo.deleteProject(id).collect{
                result=it
            }
        }
        return result
    }


    init {
        getAllProjects()
    }
}
