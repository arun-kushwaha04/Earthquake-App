package com.example.earthquakeapp.services

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object serviceBuilder {
    private const val baseUrl= "https://earthquake.usgs.gov/"

    private val logger = run {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.apply {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC
        }
    };
    private val okHttp = OkHttpClient.Builder().addInterceptor(logger);
    private val builder=Retrofit.Builder().baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttp.build())

    private val retrofit= builder.build()

    fun <T> buildService(serviceType: Class<T>):T{
        return retrofit.create(serviceType)
    }
}