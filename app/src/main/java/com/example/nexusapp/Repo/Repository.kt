package com.example.nexusapp.Repo

import android.content.SharedPreferences
import android.util.Log
import com.example.nexusapp.api.Api
import com.example.nexusapp.models.HomePageResponse.HomePageResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class Repository @Inject constructor(
    private val api: Api,
    private val token:String?
    ) {
suspend fun getHomePageInfo():Flow<Resource<HomePageResponse>>
= flow {
    emit(Resource.Loading())
try {
    val data = api.homePage(token!!)
    if(data!=null) {
        emit(Resource.Success(data,"Success fetching data"))
        Log.d("data",data.toString())
    }
    else emit(Resource.Failed("there is no data"))

}catch (e:Exception){
    emit(Resource.Failed(e.localizedMessage))
}
}
}