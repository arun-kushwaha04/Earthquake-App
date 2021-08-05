package com.example.earthquakeapp


import android.annotation.SuppressLint
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import com.example.earthquakeapp.databinding.ActivityMainBinding
import com.example.earthquakeapp.model.model
import com.example.earthquakeapp.services.earthquakeData
import com.example.earthquakeapp.services.serviceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var dateView: TextView
    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        var startDateSelector = binding.startDateValue;
        var endDateSelector = binding.endDateValue;
        var datePickerLayout = binding.datePickerLayout;
        var datePicker = binding.datePicker;
        var closeDatePicker = binding.closeDatePicker;
        var startDate = binding.startDateValue;
        var endDate = binding.endDateValue;
        val currentDateTime = LocalDateTime.now()

        startDate.text = currentDateTime.format(DateTimeFormatter.ISO_DATE)
        endDate.text = currentDateTime.format(DateTimeFormatter.ISO_DATE)



        startDateSelector.setOnClickListener(){
            datePickerLayout.visibility= View.VISIBLE
//            var date:String="";
//            date+=(datePicker.year);date+='-';
//            date+=(datePicker.month+1);date+='-';
//            date+=(datePicker.dayOfMonth);
//            startDate.text=date;
            dateView=startDate;
        }
        endDateSelector.setOnClickListener(){
            datePickerLayout.visibility= View.VISIBLE;
            dateView=endDate;
        }


        closeDatePicker.setOnClickListener(){
            datePickerLayout.visibility= View.GONE
        }

        val today = Calendar.getInstance()
        datePicker.init(today.get(Calendar.YEAR), today.get(Calendar.MONTH),
            today.get(Calendar.DAY_OF_MONTH))
        { _, year, months, day ->
            val month = months + 1
            if(currentDateTime.year >= year && currentDateTime.monthValue >= months && currentDateTime.dayOfMonth >= day)
            dateView.text="${year}-${month}-${day}"
            else dateView.text = currentDateTime.format(DateTimeFormatter.ISO_DATE)
        }




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
                    "Failed to Get Data Check Internet Connectivity2",
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