package com.example.nexusapp.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.example.nexusapp.Repo.Repository
import com.example.nexusapp.Storage.SharedPrefManager
import com.example.nexusapp.api.Api
import com.example.nexusapp.api.RetrofitClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
@Module
@InstallIn(SingletonComponent::class)
object AppModul {
        @Provides
        @Singleton
        fun createRepo(api:Api,token:String?): Repository {
            return Repository(api, token)
        }

        @Provides
        @Singleton
        fun getToken(c: Application): String?{
            return SharedPrefManager(c).token
        }
    @Provides
    @Singleton
    fun getApi(): Api {
        return RetrofitClient.instance
    }

}