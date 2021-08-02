package com.example.earthquakeapp.services

import com.example.earthquakeapp.model.model
import retrofit2.Call
import retrofit2.http.GET

interface earthquakeData {
    @GET("/query?starttime=2021-07-31&endtime=2021-08-01&minmagnitude=4.5&format=geojson")
    fun getEarthquakeData(): Call<List<model>>
}