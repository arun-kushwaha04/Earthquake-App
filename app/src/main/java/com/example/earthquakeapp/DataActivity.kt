package com.example.earthquakeapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.earthquakeapp.adapter.recycler_apdapter
import com.example.earthquakeapp.model.Features
import com.example.earthquakeapp.model.Model
import com.example.earthquakeapp.model.Properties
import com.example.earthquakeapp.services.earthquakeData
import com.example.earthquakeapp.services.serviceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Array

class DataActivity : AppCompatActivity(){
    private lateinit var data: ArrayList<Features>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data)

        val recyclerview = findViewById<RecyclerView>(R.id.recycler_view_data)

        val earthquakeDataService = serviceBuilder.buildService(earthquakeData::class.java)
        val requestCall =
            earthquakeDataService.getEarthquakeData("2021-07-31", "2021-08-01", 4.5, "geojson")
        requestCall.enqueue(object : Callback<Model> {
            override fun onResponse(call: Call<Model>, response: Response<Model>) {
                if (response.isSuccessful) {
                    val earthquakeData:Model = response.body()!!
                    Log.d("DataActivity",earthquakeData.toString())
                    data=earthquakeData.features;
                    var recyclerView = recyclerview
                    recyclerView.layoutManager = LinearLayoutManager(this@DataActivity)
                    recyclerView.layoutManager =
                        LinearLayoutManager(this@DataActivity, LinearLayoutManager.VERTICAL, false)
                    recyclerView.adapter = recycler_apdapter(this@DataActivity ,data);
                } else {
                    Toast.makeText(
                        this@DataActivity,
                        "Failed to Get Data Check Internet Connectivity",
                        Toast.LENGTH_LONG
                    ).show()
                    Log.d("DataActivity","error")
                }
            }

            override fun onFailure(call: Call<Model>, t: Throwable) {
                Toast.makeText(
                    this@DataActivity,
                    "Failed to Get Data Check Internet Connectivity2",
                    Toast.LENGTH_LONG
                ).show()
                Log.d("DataActivity", t.toString())
            }

        })




    }
}