package com.example.sharetasks.data.network

import retrofit2.Call
import retrofit2.http.GET


interface SharedListApiService {

    @GET("getMockData")
    fun getTasks(): Call<Groups>

}