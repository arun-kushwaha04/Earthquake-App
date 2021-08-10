package com.example.earthquakeapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.earthquakeapp.adapter.recycler_apdapter
import com.example.earthquakeapp.model.Features
import com.example.earthquakeapp.model.Model
import com.example.earthquakeapp.services.earthquakeData
import com.example.earthquakeapp.services.serviceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DataActivity : AppCompatActivity(){
    private lateinit var data: List<Features>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data)

        val date1=intent.getStringExtra("date1").toString()
        val date2=intent.getStringExtra("date2").toString()
        val magnitude = intent.getStringExtra("magnitude")!!.toDouble()
        Log.d("MainActivity", date1)
        Log.d("MainActivity", date2)
        Log.d("MainActivity", magnitude.toString())
        val recyclerview = findViewById<RecyclerView>(R.id.recycler_view_data)
        val progressBar = findViewById<ProgressBar>(R.id.progress_bar)
        val message = findViewById<TextView>(R.id.noDataMessage)

        val earthquakeDataService = serviceBuilder.buildService(earthquakeData::class.java)
        val requestCall =
            earthquakeDataService.getEarthquakeData(date1, date2, magnitude, "geojson")
        requestCall.enqueue(object : Callback<Model> {
            override fun onResponse(call: Call<Model>, response: Response<Model>) {
                if (response.isSuccessful) {
                    val earthquakeData: Model = response.body()!!
                    val temp = earthquakeData.features;
                    data = temp.filter{
                        it.properties.place!=null && it.properties.place.split("of").size >= 2
                    }
                   if( data.isNotEmpty()) {
                       recyclerview.layoutManager = LinearLayoutManager(this@DataActivity)
                       recyclerview.layoutManager =
                           LinearLayoutManager(
                               this@DataActivity,
                               LinearLayoutManager.VERTICAL,
                               false
                           )
                       recyclerview.adapter = recycler_apdapter(this@DataActivity, data);
                   }
                    else{
                        message.visibility=View.VISIBLE
                   }
                       progressBar.visibility = View.GONE
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
                    "Failed to Get Data Check Internet Connectivity",
                    Toast.LENGTH_LONG
                ).show()
                Log.d("DataActivity", t.toString())
            }

        })




    }
}