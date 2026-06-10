package com.proiect.firstaidapp

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object PostTestRetrofit {

    private const val BASE_URL = "https://httpbin.org/"

    val api: PostTestApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PostTestApi::class.java)
    }
}
