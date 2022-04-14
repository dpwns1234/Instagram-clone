package com.instagram.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

class ApiClient {

    @GET("main.json")
    fun getMain() {

    }

    companion object {

        fun create(): ApiClient {
            return Retrofit.Builder()
                .baseUrl("")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiClient::class.java)
        }
    }
}