package com.instagram.network

import com.google.gson.GsonBuilder
import com.instagram.model.Post
import com.instagram.model.PreviewPost
import com.instagram.model.Profile
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


interface ApiClient {

    //@GET("movie/readMovieList")
    //suspend fun getMovieList() : List<MovieList>

    // profile
    @GET("users/{uid}/profiles.json")
    suspend fun getProfile(@Path("uid") uid: String): Profile

    @GET("users/{userUid}/profiles/posts.json")
    suspend fun getPreviewPosts(@Path("userUid") userUid: String) : List<PreviewPost>

    @GET("users/{userUid}/following_list.json")
    suspend fun getFollowingList(@Path("userUid") userUid: String) : Response<MutableList<String>>


    // home
    @GET("posts.json")
    suspend fun getPosts() : List<Post>?


    companion object {
        private const val baseUrl = "https://instagram-android-65931-default-rtdb.asia-southeast1.firebasedatabase.app/"

        fun create(): ApiClient {
            val logger = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()


            val gson = GsonBuilder().setLenient().create()
            return Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(ApiClient::class.java)
        }
    }
}