package com.jheank16oz.questionform.place

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by ICATECH on 8/05/18.
 * Developer Jhean Carlos Piñeros Diaz
 */

class Prediction {

    @SerializedName("description")
    @Expose
    val description: String? = null

    @get:Deprecated("")
    val id: String? = null

    @SerializedName("structured_formatting")
    @Expose
    val structuredFormatting: StructuredFormatting? = null

    @SerializedName("place_id")
    @Expose
    val placeId: String? = null

    @get:Deprecated("")
    val reference: String? = null

    override fun toString(): String {
        return description?:""
    }
}
