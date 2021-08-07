package com.example.earthquakeapp.services

import com.example.earthquakeapp.model.Model
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface earthquakeData {
    @GET("fdsnws/event/1/query")
    fun getEarthquakeData(@Query("starttime") starttime:String?, @Query("endtime") endtime:String?, @Query("minmagnitude") minmagnitude: Double
                          , @Query("format") format:String?): Call<Model>
}