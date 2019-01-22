package com.jheank16oz.questionform.place

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Result {

    @SerializedName("geometry")
    @Expose
    var geometry: Geometry? = null
}
