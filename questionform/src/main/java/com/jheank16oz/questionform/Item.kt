package com.jheank16oz.questionform

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Item(@SerializedName("id")
           @Expose var id: Int, @SerializedName("question_name")
           @Expose var tittle: String?, @SerializedName("value_catalogue")
           @Expose var catalogue: String?, @SerializedName("childs")
           @Expose var dependants: List<Int>?) : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString(),
            parcel.readString(),
            parcel.createIntArray().toList())

    override fun toString(): String {
        return if (tittle != null) tittle!! else ""
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(tittle)
        parcel.writeString(catalogue)
        parcel.writeIntList(dependants)
    }

    private fun Parcel.writeIntList(input:List<Int>?) {
        input?.size?.let { writeInt(it) } // Save number of elements.
        input?.forEach(this::writeInt) // Save each element.
    }


    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Item> {
        override fun createFromParcel(parcel: Parcel): Item {
            return Item(parcel)
        }

        override fun newArray(size: Int): Array<Item?> {
            return arrayOfNulls(size)
        }
    }


}