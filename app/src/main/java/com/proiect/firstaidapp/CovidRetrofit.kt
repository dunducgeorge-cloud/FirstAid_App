package com.proiect.firstaidapp

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object CovidRetrofit {

    private const val BASE_URL = "https://disease.sh/"

    val api: CovidApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CovidApi::class.java)
    }
}
