package com.example.earthquakeapp


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.example.earthquakeapp.model.model
import com.example.earthquakeapp.services.earthquakeData
import com.example.earthquakeapp.services.serviceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val earthquakeDataService = serviceBuilder.buildService(earthquakeData::class.java)
        val requestCall =
            earthquakeDataService.getEarthquakeData("2021-07-31", "2021-08-01", 4.5, "geojson")
        requestCall.enqueue(object : Callback<List<model>> {
            override fun onResponse(call: Call<List<model>>, response: Response<List<model>>) {
                if (response.isSuccessful) {
                    val earthquakeData: List<model> = response.body()!!
                } else {
                    Toast.makeText(
                        this@MainActivity,
                        "Failed to Get Data Check Internet Connectivity",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            override fun onFailure(call: Call<List<model>>, t: Throwable) {
                Toast.makeText(
                    this@MainActivity,
                    "Failed to Get Data Check Internet Connectivity",
                    Toast.LENGTH_LONG
                ).show()
            }

        })

        val languages = resources.getStringArray(R.array.Languages)
        val spinner = findViewById<Spinner>(R.id.minMagValue)
        if (spinner != null) {
            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item, languages
            )
            spinner.adapter = adapter
        }
    }
}