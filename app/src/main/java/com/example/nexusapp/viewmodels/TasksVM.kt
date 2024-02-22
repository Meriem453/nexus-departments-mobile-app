package com.example.nexusapp.viewmodels

import androidx.lifecycle.ViewModel
import com.example.nexusapp.Repo.Repository
import javax.inject.Inject

class TasksVM @Inject constructor(
    private val repo: Repository
) : ViewModel() {

}