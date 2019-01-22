package com.jheank16oz.questionform.geocode

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



/**
 *
 *  <p>ResultGeoCode</p>
 */
class ResultGeoCode {

    @SerializedName("results")
    @Expose
    var results: List<Result>? = null
    @SerializedName("status")
    @Expose
    var status: String? = null
    @SerializedName("error_message")
    @Expose
    var errorMessage: String? = null


}