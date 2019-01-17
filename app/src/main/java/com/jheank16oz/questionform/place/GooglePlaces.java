package com.jheank16oz.questionform.place;

/**
 * Created by ICATECH on 8/05/18.
 * Developer Jhean Carlos Piñeros Diaz
 */

import com.google.android.gms.maps.model.LatLng;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class GooglePlaces {

    private static final String GOOGLE_MAPS_URL = "https://maps.googleapis.com/";

    private String apikey;

    private Webservice mWebservice;

    public GooglePlaces(String apikey) {
        this.apikey = apikey;

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(GOOGLE_MAPS_URL)
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit mRetrofit = builder.build();
        mWebservice = mRetrofit.create(Webservice.class);

    }

    public void autocomplete(String input, Callback<AutocompleteResult> callback) {
        mWebservice.getAutoComplete(apikey,input).enqueue(callback);
    }

    public void getLocation(String placeId, final PlaceListener placeListener){
        mWebservice.getLocationByPlaceId(placeId,apikey).enqueue(new Callback<ResultPlaceId>() {
            @Override
            public void onResponse(Call<ResultPlaceId> call, Response<ResultPlaceId> response) {

                if (response.isSuccessful()){
                    ResultPlaceId resultPlaceId = response.body();
                    if (resultPlaceId.status.equalsIgnoreCase("ok")) {

                        placeListener.onSuccess(new LatLng(response.body().result.geometry.location.lat, response.body().result.geometry.location.lng));
                    }
                }
            }

            @Override
            public void onFailure(Call<ResultPlaceId> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public interface PlaceListener{
        void onSuccess(LatLng latLng);
    }


}
