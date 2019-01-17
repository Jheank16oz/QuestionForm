package com.jheank16oz.questionform.place;

/**
 * Created by ICATECH on 8/05/18.
 * Developer Jhean Carlos Piñeros Diaz
 */

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jheank16oz.questionform.R;


public class PlacesApiWebserviceAdapter
        extends FilterWithSpaceAdapter<Prediction>
{




    public PlacesApiWebserviceAdapter(Context context)
    {
        super(context, R.layout.simple_list_expandible_item_2, android.R.id.text1);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View row = super.getView(position, convertView, parent);

        // Sets the primary and secondary text for a row.
        // Note that getPrimaryText() and getSecondaryText() return a CharSequence that may contain
        // styling based on the given CharacterStyle.

        Prediction item = getItem(position);

        TextView textView1 =  row.findViewById(android.R.id.text1);
        TextView textView2 =  row.findViewById(android.R.id.text2);
        textView1.setText(item.getStructuredFormatting().mainText);
        textView2.setText(item.getStructuredFormatting().secondaryText);// Proceso vista 4

        return row;
    }


}
