package com.jheank16oz.questionform.place

/**
 * Created by ICATECH on 8/05/18.
 * Developer Jhean Carlos Piñeros Diaz
 */

import com.google.android.gms.maps.model.LatLng
import com.jheank16oz.questionform.ResultGeoCode

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class GoogleApiRepository(private val apikey: String) {

    private val mWebservice: Webservice

    init {

        val builder = Retrofit.Builder()
                .baseUrl(GOOGLE_MAPS_URL)
                .addConverterFactory(GsonConverterFactory.create())
        val mRetrofit = builder.build()
        mWebservice = mRetrofit.create(Webservice::class.java)

    }

    fun autocomplete(input: String, callback: Callback<AutocompleteResult>) {
        mWebservice.getAutoComplete(apikey, input).enqueue(callback)
    }

    fun getLocation(placeId: String, placeListener: PlaceListener) {
        mWebservice.getLocationByPlaceId(placeId, apikey).enqueue(object : Callback<ResultPlaceId> {
            override fun onResponse(call: Call<ResultPlaceId>, response: Response<ResultPlaceId>) {

                if (response.isSuccessful) {
                    val resultPlaceId = response.body()
                    if (resultPlaceId.status.equals("ok", ignoreCase = true)) {
                        response.body().result?.geometry?.location?.let {
                            placeListener.onSuccess(LatLng(it.lat, it.lng))
                        }

                    }
                }
            }

            override fun onFailure(call: Call<ResultPlaceId>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }


    fun getDirection(latLng: LatLng, geocodingListener: GeocodingListener) {
        mWebservice.getDirection("${latLng.latitude},${latLng.longitude}", apikey).enqueue(object : Callback<ResultGeoCode> {
            override fun onResponse(call: Call<ResultGeoCode>, response: Response<ResultGeoCode>) {

                if (response.isSuccessful) {
                    val resultPlaceId = response.body()
                    if (resultPlaceId.status.equals("ok", ignoreCase = true)) {
                        response.body()?.results?.get(0)?.formattedAddress?.let {
                            geocodingListener.onSuccess(it)
                        }
                    }else{
                        geocodingListener.failure(response.body().errorMessage?:"Error al consultar referencia")
                    }
                }else{
                    geocodingListener.failure(response.message()?:response.errorBody().string()?:"Error de conexión")
                }
            }

            override fun onFailure(call: Call<ResultGeoCode>, t: Throwable) {
                t.printStackTrace()
                geocodingListener.failure("Error de conexión")

            }
        })
    }

    interface PlaceListener {
        fun onSuccess(latLng: LatLng)
    }


    interface GeocodingListener{
        fun onSuccess(directionStr:String?)
        fun failure(msg:String)
    }

    companion object {

        private val GOOGLE_MAPS_URL = "https://maps.googleapis.com/"
    }


}
