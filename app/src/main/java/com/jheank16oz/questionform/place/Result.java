package com.jheank16oz.questionform.place;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result{

    @SerializedName("geometry")
    @Expose
    public Geometry geometry;
}
