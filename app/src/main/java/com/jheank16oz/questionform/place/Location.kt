package com.jheank16oz.questionform.place

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Location {

    @SerializedName("lat")
    @Expose
    var lat: Double = 0.toDouble()
    @SerializedName("lng")
    @Expose
    var lng: Double = 0.toDouble()

}
