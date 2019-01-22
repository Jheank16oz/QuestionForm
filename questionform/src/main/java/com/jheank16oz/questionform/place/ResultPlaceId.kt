package com.jheank16oz.questionform.place

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by ICATECH on 9/05/18.
 * Developer Jhean Carlos Piñeros Diaz
 */

class ResultPlaceId {
    @SerializedName("result")
    @Expose
    var result: Result? = null

    @SerializedName("status")
    @Expose
    var status: String? = null
}

