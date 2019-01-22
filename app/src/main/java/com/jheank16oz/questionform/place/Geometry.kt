package com.jheank16oz.questionform.place

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Geometry {

    @SerializedName("location")
    @Expose
    var location: Location? = null
}
