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
import com.example.nexusapp.models.TaskResponse
import com.example.nexusapp.models.TeamResponse
import kotlinx.coroutines.launch
import javax.inject.Inject

class TasksVM @Inject constructor(
    private val repo: Repository,
    private val context:Application
) : ViewModel() {

    var tasks by mutableStateOf<Resource<List<TaskResponse>>>(Resource.Loading())
    var teams by mutableStateOf<Resource<List<TeamResponse>>>(Resource.Loading())

    fun getAllTasks(){
        viewModelScope.launch {
            repo.getAllTasks().collect{
                tasks=it
            }
        }
    }

    fun addTasks(taskResponse: TaskResponse){

        viewModelScope.launch {
            repo.addTask(taskResponse).collect{
                if(it !is Resource.Loading)
                    Toast.makeText(context,it.message, Toast.LENGTH_LONG).show()
            }
        }

    }

    fun updateTask(taskResponse: TaskResponse) {

        viewModelScope.launch {
            repo.updateTask(taskResponse).collect{
                if(it !is Resource.Loading)
                    Toast.makeText(context,it.message, Toast.LENGTH_LONG).show()
            }
        }
    }

    fun deleteTask(id:Int) {

        viewModelScope.launch {
            repo.deleteTask(id).collect{
                if(it !is Resource.Loading)
                    Toast.makeText(context,it.message, Toast.LENGTH_LONG).show()
            }
        }
    }


    init {
        getAllTasks()
        viewModelScope.launch {
            repo.getAllTeams().collect{
                teams=it
            }
        }
    }
}