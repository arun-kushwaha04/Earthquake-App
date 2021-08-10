package com.example.earthquakeapp.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.earthquakeapp.R
import com.example.earthquakeapp.model.Features
import com.example.earthquakeapp.model.Properties
import java.text.SimpleDateFormat
import java.util.*

class recycler_apdapter(
    private val context: Context?,
    private val list: List<Features>
) : RecyclerView.Adapter<recycler_apdapter.ImageViewHolder>(){
    class ImageViewHolder(view: View) : RecyclerView.ViewHolder(view){
        @SuppressLint("SimpleDateFormat")
        private fun convertDate(timeInMilliseconds: Long, dateFormat: String?): String? {
            val dateObject = Date(timeInMilliseconds)
            val dateFormatter = SimpleDateFormat(dateFormat)
            return dateFormatter.format(dateObject)
        }
        private val mag:TextView = itemView.findViewById(R.id.earthquake_mag)
        private val location1:TextView = itemView.findViewById(R.id.location_1)
        private val location2:TextView = itemView.findViewById(R.id.location_2)
        private val date:TextView = itemView.findViewById(R.id.date)
        private val time:TextView = itemView.findViewById(R.id.time)
        fun bindView(data:Properties){
            mag.text = data.mag
            val temp = data.place.split("of")
            location1.text=temp[0]
            location2.text=temp[1].drop(1)
            date.text=convertDate(data.time,"yyyy-MM-dd")
            time.text=convertDate(data.time,"hh:mm")
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder =
        ImageViewHolder(
            LayoutInflater.from(context).inflate(R.layout.recyler_card_view,parent,false)
        )
    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {

            holder.bindView(list[position].properties)

    }
    override fun getItemCount(): Int {
        return list.size
    }
}