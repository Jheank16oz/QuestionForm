package com.jheank16oz.questionform.place

/**
 * Created by ICATECH on 8/05/18.
 * Developer Jhean Carlos Piñeros Diaz
 */

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.jheank16oz.questionform.R


class PlacesApiWebserviceAdapter(context: Context) : FilterWithSpaceAdapter<Prediction>(context, R.layout.simple_list_expandible_item_2, android.R.id.text1) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val row = super.getView(position, convertView, parent)

        // Sets the primary and secondary text for a row.
        // Note that getPrimaryText() and getSecondaryText() return a CharSequence that may contain
        // styling based on the given CharacterStyle.

        val item = getItem(position)

        val textView1 = row.findViewById<TextView>(android.R.id.text1)
        val textView2 = row.findViewById<TextView>(android.R.id.text2)
        textView1.text = item.structuredFormatting?.mainText
        textView2.text = item.structuredFormatting?.secondaryText// Proceso vista 4

        return row
    }


}
