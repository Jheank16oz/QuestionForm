package com.jheank16oz.questionform

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



/**
 *
 *  <p>Properties</p>
 */
class Properties() : Parcelable {

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
    @SerializedName("options")
    @Expose
    var items: ArrayList<Item>? = null
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
    @SerializedName("form")
    @Expose
    var form: ArrayList<Question>? = null

    constructor(parcel: Parcel) : this() {
        placeholder = parcel.readString()
        inputType = parcel.readString()
        length = parcel.readInt()
        min = parcel.readDouble()
        max = parcel.readDouble()
        minTime = parcel.readString()
        maxTime = parcel.readString()
        minDate = parcel.readString()
        maxDateTime = parcel.readString()
        minDateTime = parcel.readString()
        maxDate = parcel.readString()
        minSizeMb = parcel.readInt()
        count = parcel.readInt()
        formCount = parcel.readInt()
        formId = parcel.readInt()
        formTittle = parcel.readString()
        formContent = parcel.readString()
        form = parcel.createTypedArrayList(Question)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(placeholder)
        parcel.writeString(inputType)
        parcel.writeInt(length)
        parcel.writeDouble(min)
        parcel.writeDouble(max)
        parcel.writeString(minTime)
        parcel.writeString(maxTime)
        parcel.writeString(minDate)
        parcel.writeString(maxDateTime)
        parcel.writeString(minDateTime)
        parcel.writeString(maxDate)
        parcel.writeInt(minSizeMb)
        parcel.writeInt(count)
        parcel.writeInt(formCount)
        parcel.writeInt(formId)
        parcel.writeString(formTittle)
        parcel.writeString(formContent)
        parcel.writeTypedList(form)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Properties> {
        override fun createFromParcel(parcel: Parcel): Properties {
            return Properties(parcel)
        }

        override fun newArray(size: Int): Array<Properties?> {
            return arrayOfNulls(size)
        }
    }


}