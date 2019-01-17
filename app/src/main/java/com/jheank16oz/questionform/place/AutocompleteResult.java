package com.jheank16oz.questionform.place;

import java.util.Iterator;
import java.util.List;

/**
 * Created by ICATECH on 8/05/18.
 * Developer Jhean Carlos Piñeros Diaz
 */

public class AutocompleteResult implements Iterable<Prediction> {

    public List<Prediction> predictions;

    @Override
    public Iterator<Prediction> iterator( ) {
        return this.predictions.iterator( );
    }

    public int size( ) {
        return this.predictions.size( );
    }

}
