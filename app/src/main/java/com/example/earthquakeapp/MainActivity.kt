package com.example.earthquakeapp


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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
        val requestCall =  earthquakeDataService.getEarthquakeData()
        requestCall.enqueue(object: Callback<List<model>> {
            override fun onResponse(call: Call<List<model>>, response: Response<List<model>>) {
                if(response.isSuccessful){
                    val earthquakeData:List<model> = response.body()!!
                    Log.d("MainActivity", earthquakeData.toString());
                    Toast.makeText(this@MainActivity,"data fetched", Toast.LENGTH_LONG).show()
                }
                else Toast.makeText(this@MainActivity,"failed here", Toast.LENGTH_LONG).show()
            }

            override fun onFailure(call: Call<List<model>>, t: Throwable) {
                Log.d("MainActivity", "Error Occurred");
                Toast.makeText(this@MainActivity,"failed", Toast.LENGTH_LONG).show()
            }

        })
    }
}