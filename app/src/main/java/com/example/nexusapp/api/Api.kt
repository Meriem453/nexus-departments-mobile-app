package com.example.nexusapp.api

import com.example.nexusapp.models.HomePageResponse.HomePageResponse
import com.example.nexusapp.models.LoginResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface Api {

    @FormUrlEncoded
    @POST("user/login")
    fun userLogin(
        @Field("email") email:String,
        @Field("password") password: String
    ): Call<LoginResponse>

    @GET("user/departmentOverview/{token}")
    suspend fun homePage(
        @Path("token") token:String
    ):HomePageResponse
}