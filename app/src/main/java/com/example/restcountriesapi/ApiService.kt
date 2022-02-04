package com.example.restcountriesapi

import com.google.gson.annotations.SerializedName
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface ApiService {
    @GET("all")
    suspend fun getAllCountries():Response<List<CountriesResponseItem>>

    @GET
    suspend fun getCountryByName(@Url url: String):Response<List<CountriesResponseItem>>
}