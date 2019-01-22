package com.jheank16oz.questionform.place

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by ICATECH on 8/05/18.
 * Developer Jhean Carlos Piñeros Diaz
 */

class StructuredFormatting {
    @SerializedName("main_text")
    @Expose
    var mainText: String? = null
    @SerializedName("secondary_text")
    @Expose
    var secondaryText: String? = null
}
