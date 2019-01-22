package com.jheank16oz.questionform.place

/**
 * Created by ICATECH on 8/05/18.
 * Developer Jhean Carlos Piñeros Diaz
 */

class AutocompleteResult : Iterable<Prediction> {

    var predictions: List<Prediction>? = null

    override fun iterator(): Iterator<Prediction> {
        return this.predictions!!.iterator()
    }

    fun size(): Int {
        return this.predictions!!.size
    }

}
