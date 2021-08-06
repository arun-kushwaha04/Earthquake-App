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
import com.example.earthquakeapp.model.Model
import com.example.earthquakeapp.services.earthquakeData
import com.example.earthquakeapp.services.serviceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.InvocationTargetException
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var dateView: TextView
    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val startDateSelector = binding.startDateValue
        val endDateSelector = binding.endDateValue
        val datePickerLayout = binding.datePickerLayout
        val datePicker = binding.datePicker
        val closeDatePicker = binding.closeDatePicker
        val startDate = binding.startDateValue
        val endDate = binding.endDateValue
        val currentDateTime = LocalDateTime.now()

        startDate.text = currentDateTime.format(DateTimeFormatter.ISO_DATE)
        endDate.text = currentDateTime.format(DateTimeFormatter.ISO_DATE)
        val format = SimpleDateFormat("yyyy-MM-dd");
        val date = format.parse(currentDateTime.format(DateTimeFormatter.ISO_DATE))



        startDateSelector.setOnClickListener(){
            datePickerLayout.visibility= View.VISIBLE
            dateView=startDate;
        }
        endDateSelector.setOnClickListener(){
            datePickerLayout.visibility= View.VISIBLE;
            dateView=endDate;
        }


        closeDatePicker.setOnClickListener(){
            val currDate = format.parse(dateView.text.toString())!!
            if( currDate > date){
                dateView.text=currentDateTime.format(DateTimeFormatter.ISO_DATE)
            }
            val date1= format.parse(startDate.text.toString())!!
            val date2= format.parse(endDate.text.toString())!!
            if(date1>date2){
                startDate.text=endDate.text;
            }
            datePickerLayout.visibility= View.GONE
        }

        val today = Calendar.getInstance()
        datePicker.init(today.get(Calendar.YEAR), today.get(Calendar.MONTH),
            today.get(Calendar.DAY_OF_MONTH))
        { _, year, months, day ->
            val month = months + 1
            if (month < 10 && day < 10) {
                dateView.text = "${year}-0${month}-0${day}"
            }
            else if (month < 10 && day >= 10) {
                dateView.text = "${year}-0${month}-${day}"
            }
            else if (month >= 10 && day < 10) {
                dateView.text = "${year}-${month}-0${day}"
            } else {
                dateView.text = "${year}-${month}-${day}"
            }
        }

        val earthquakeDataService = serviceBuilder.buildService(earthquakeData::class.java)
        val requestCall =
            earthquakeDataService.getEarthquakeData("2021-07-31", "2021-08-01", 4.5, "geojson")
        requestCall.enqueue(object : Callback<List<Model>> {
            override fun onResponse(call: Call<List<Model>>, response: Response<List<Model>>) {
                if (response.isSuccessful) {
                    val earthquakeData: List<Model> = response.body()!!
                } else {
                    Toast.makeText(
                        this@MainActivity,
                        "Failed to Get Data Check Internet Connectivity",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            override fun onFailure(call: Call<List<Model>>, t: Throwable) {
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