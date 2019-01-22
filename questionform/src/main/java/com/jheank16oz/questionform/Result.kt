package com.jheank16oz.questionform

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



/**
 *
 *  <p>Result</p>
 */
class Result {

    @SerializedName("formatted_address")
    @Expose
    var formattedAddress: String? = null

}