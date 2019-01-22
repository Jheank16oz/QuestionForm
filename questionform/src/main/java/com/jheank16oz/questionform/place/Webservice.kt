package com.jheank16oz.questionform.place


import com.jheank16oz.questionform.ResultGeoCode

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Interfaz para realizar solicitudes, trabajando bajo
 * el client Rest Retrofit. Aquí se colocan los parámetros
 * que va a recibir cada webservice
 *
 * @author Ikatech, solutions
 */
interface Webservice {


    @GET("maps/api/place/autocomplete/json")
    fun getAutoComplete(@Query("key") key: String, @Query("input") input: String): Call<AutocompleteResult>

    @GET("maps/api/place/details/json")
    fun getLocationByPlaceId(@Query("placeid") placeId: String, @Query("key") key: String): Call<ResultPlaceId>


    @GET("maps/api/geocode/json")
    fun getDirection(@Query("latlng") latlng: String, @Query("key") key: String): Call<ResultGeoCode>


}

