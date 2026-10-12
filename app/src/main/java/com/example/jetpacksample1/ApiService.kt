package com.example.jetpacksample1

import retrofit2.http.GET


interface ApiService {
        @GET("posts")
        suspend fun getPosts(): List<Post>

    }
