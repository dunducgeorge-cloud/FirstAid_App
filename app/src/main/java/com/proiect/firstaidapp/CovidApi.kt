package com.proiect.firstaidapp

import retrofit2.http.GET

interface CovidApi {
    @GET("v3/covid-19/countries")
    suspend fun getCountries(): List<CountryCovid>
}
