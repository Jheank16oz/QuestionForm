package com.jheank16oz.questionform

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



/**
 *
 *  <p>Properties</p>
 */
class Properties {

    @SerializedName("placeholder")
    @Expose
    var placeholder: String? = null
    @SerializedName("input_type")
    @Expose
    var inputType: String? = null
    @SerializedName("length")
    @Expose
    var length: Int = 0
    @SerializedName("min")
    @Expose
    var min: Double = 0.0
    @SerializedName("max")
    @Expose
    var max: Double = 0.0
    @SerializedName("min_time")
    @Expose
    var minTime: String? = null
    @SerializedName("max_time")
    @Expose
    var maxTime: String? = null
    @SerializedName("min_date")
    @Expose
    var minDate: String? = null
    @SerializedName("max_datetime")
    @Expose
    var maxDateTime: String? = null
    @SerializedName("min_datetime")
    @Expose
    var minDateTime: String? = null
    @SerializedName("max_date")
    @Expose
    var maxDate: String? = null
    @SerializedName("min_size_mb")
    @Expose
    var minSizeMb: Int = 0
    @SerializedName("count")
    @Expose
    var count: Int = 0
    @SerializedName("items")
    @Expose
    var items: List<Item>? = null
    @SerializedName("form_count")
    @Expose
    var formCount: Int = 0
    @SerializedName("form_id")
    @Expose
    var formId: Int = 0
    @SerializedName("form_tittle")
    @Expose
    var formTittle: String? = null
    @SerializedName("form_content")
    @Expose
    var formContent: String? = null

}