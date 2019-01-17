package com.jheank16oz.questionform.place;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ICATECH on 8/05/18.
 * Developer Jhean Carlos Piñeros Diaz
 */

public class Prediction {

    @SerializedName("description")
    @Expose
    private String description;

    private String id;

    @SerializedName("structured_formatting")
    @Expose
    private StructuredFormatting structuredFormatting;

    @SerializedName("place_id")
    @Expose
    private String placeId;

    private String reference;



    public String getDescription( ) {
        return this.description;
    }

    @Deprecated
    public String getId( ) {
        return this.id;
    }


    public String getPlaceId( ) {
        return this.placeId;
    }

    @Deprecated
    public String getReference( ) {
        return this.reference;
    }

    public StructuredFormatting getStructuredFormatting() {
        return structuredFormatting;
    }

    @Override
    public String toString() {
        return description;
    }
}
