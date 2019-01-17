package com.jheank16oz.questionform.place;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ICATECH on 8/05/18.
 * Developer Jhean Carlos Piñeros Diaz
 */

public class StructuredFormatting {
    @SerializedName("main_text")
    @Expose
    public String mainText;
    @SerializedName("main_text_matched_substrings")
    @Expose
    public List<MatchedSubstring> mainTextMatchedSubstrings = null;
    @SerializedName("secondary_text")
    @Expose
    public String secondaryText;
}
