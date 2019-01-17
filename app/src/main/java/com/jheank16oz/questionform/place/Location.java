package com.jheank16oz.questionform.place;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Location{

    @SerializedName("lat")
    @Expose
    public double lat;
    @SerializedName("lng")
    @Expose
    public double lng;

}
