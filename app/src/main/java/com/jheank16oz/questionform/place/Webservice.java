package com.jheank16oz.questionform.place;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Interfaz para realizar solicitudes, trabajando bajo
 * el client Rest Retrofit. Aquí se colocan los parámetros
 * que va a recibir cada webservice
 *
 * @author Ikatech, solutions
 */
public interface Webservice {


    @GET("maps/api/place/autocomplete/json")
    Call<AutocompleteResult> getAutoComplete(@Query("key") String key, @Query("input") String input);

    @GET("maps/api/place/details/json")
    Call<ResultPlaceId> getLocationByPlaceId(@Query("placeid") String placeId, @Query("key") String key);



}

