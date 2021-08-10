package com.example.earthquakeapp.model

import java.lang.reflect.Array


data class Model (
    val type:String,
    val metaData: Metadata,
    val features: ArrayList<Features>,
    val bbox: ArrayList<Float>
        )

data class Metadata(
 val generated: Int,
 val url: String,
 val title: String,
 val status: Int,
 val api: Int,
 val count: Int,
)

data class Features (

    val type:String,
    val properties:Properties,
    val geometry:Geometry,
    val id:String

        )
data class Properties(
    val mag:String,
    val place:String,
    val time:Long,
    val updated:Long,
    val tz:String,
    val url:String,
    val detail: String,
    val felt:Float,
    val cdi:Float,
    val mni:Float,
    val alert:String,
    val status: String,
    val tsunami:Int,
    val sig:Int,
    val net:String,
    val code:String,
    val ids:String,
    val sources:String,
    val types:String,
    val nst:Int,
    val dmin:Float,
    val rms:Float,
    val gap:Float,
    val magType:String,
    val type:String,
    val title:String,
)
data class Geometry(
    val type: String,
    val coordinates:ArrayList<Float>,
)

