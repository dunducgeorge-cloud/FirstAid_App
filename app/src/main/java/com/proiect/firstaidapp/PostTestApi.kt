package com.proiect.firstaidapp

import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.POST

interface PostTestApi {
    @POST("post")
    suspend fun sendData(@Body data: Map<String, String>): ResponseBody
}
