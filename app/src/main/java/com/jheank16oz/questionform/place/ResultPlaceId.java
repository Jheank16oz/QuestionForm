package com.jheank16oz.questionform.place;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ICATECH on 9/05/18.
 * Developer Jhean Carlos Piñeros Diaz
 */

public class ResultPlaceId {
    @SerializedName("result")
    @Expose
    public Result result;

    @SerializedName("status")
    @Expose
    public String status;
}

