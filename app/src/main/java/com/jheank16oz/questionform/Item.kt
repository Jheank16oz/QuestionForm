package com.jheank16oz.questionform

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Item {
    @SerializedName("id")
    @Expose
    var id: Int = 0
    @SerializedName("question_name")
    @Expose
    var tittle: String? = null
    @SerializedName("value_catalogue")
    @Expose
    var catalogue: String? = null
    @SerializedName("childs")
    @Expose
    var dependants: List<Int>? = null

    override fun toString(): String {
        return if (tittle != null) tittle!! else ""
    }

    constructor(id:Int, tittle:String ){
        this.id = id
        this.tittle = tittle
    }

}