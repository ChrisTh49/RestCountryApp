package com.example.restcountriesapi

import com.google.gson.annotations.SerializedName

data class CountriesResponseItem(
    val flags: Flags,
    val name: Name,
)

data class Flags(
    @SerializedName("png") val flagImage: String,
)

data class Name(
    @SerializedName("common") val countryNames: String,
)