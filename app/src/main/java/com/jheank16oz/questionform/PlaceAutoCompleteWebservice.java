package com.jheank16oz.questionform;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.jheank16oz.questionform.place.AutocompleteResult;
import com.jheank16oz.questionform.place.GooglePlaces;
import com.jheank16oz.questionform.place.PlacesApiWebserviceAdapter;
import com.jheank16oz.questionform.place.Prediction;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ICATECH on 9/05/18.
 * Developer Jhean Carlos Piñeros Diaz
 */

public class PlaceAutoCompleteWebservice extends CardView {

    private View mRoot;
    private GooglePlaces mClient;
    private AutoCompleteTextView mSearchView;
    private PlacesApiWebserviceAdapter mAdapter;
    private PlaceWebserviceListener mPlaceWebserviceListener;

    /**
     * objetivo para almacenar el caracter inicial en el cual se  empezarará
     * a consultar predicciones
     */
    private int mStartLimit = 0;

    public PlaceAutoCompleteWebservice(Context context) {
        super(context);
        initialize(context, null, 0);
    }

    public PlaceAutoCompleteWebservice(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context, attrs, 0);

    }

    public PlaceAutoCompleteWebservice(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialize(context, attrs, defStyle);

    }

    private void initialize(Context context, AttributeSet attrs, int defStyle) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);

        if (inflater != null) {
            mRoot = inflater.inflate(R.layout.place_autocomplete_webservice, this, true);
            mSearchView = mRoot.findViewById(R.id.autocomplete_places);
            setCardBackgroundColor(getResources().getColor(R.color.white));
            // escuchador de el boton de busqueda
            mRoot.findViewById(R.id.search).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    search();
                }
            });

            // limpiador de busquedas
            mRoot.findViewById(R.id.clear).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mSearchView.setText(null);
                }
            });

            // adaptador de predicciones de place
            mAdapter = new PlacesApiWebserviceAdapter(getContext());
            mSearchView.setAdapter(mAdapter);

            // buscador de boton enter con el teclado
            mSearchView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                        search();
                    }
                    return false;
                }
            });

            mSearchView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    selectPlace(mAdapter.getItem(position));
                }
            });
        }


    }

    public void selectPlace(Prediction prediction){
        mClient.getLocation(prediction.getPlaceId(), new GooglePlaces.PlaceListener() {
            @Override
            public void onSuccess(LatLng latLng) {
               mPlaceWebserviceListener.onPlaceSelected(latLng);
            }
        });
    }

    public void initializeAutocomplete(int startLimit, String mApiKey, PlaceWebserviceListener placeWebserviceListener) {
        if (mApiKey == null){
            throw new IllegalArgumentException("Invalid api key");
        }
        mStartLimit = startLimit;
        mPlaceWebserviceListener = placeWebserviceListener;
        mClient = new GooglePlaces(mApiKey);
        mSearchView.addTextChangedListener(mTextWatcher);

    }




    /**
     * Validaciones cuando el texto cambia y así realizamos el filtro
     */
    private TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            setClearVisibility(s.toString().isEmpty(),s.length() <= mStartLimit);

            if (s.length() > mStartLimit){

                int lastIndex = s.length()-1;
                if (!(lastIndex == mStartLimit)) {
                    // validamos si el último caracter es un espacio (ASCII = 32), no autocompletamos
                    if (s.charAt(lastIndex) == 32) {
                        return;
                    }
                }

                mClient.autocomplete(String.valueOf(s), new Callback<AutocompleteResult>() {
                    @Override
                    public void onResponse(Call<AutocompleteResult> call, Response<AutocompleteResult> response) {
                        if (response.isSuccessful())
                        {
                            mAdapter.clear();
                            mAdapter.addAll(response.body().predictions);

                        }


                    }
                    @Override
                    public void onFailure(Call<AutocompleteResult> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
            }
        }

        @Override
        public void afterTextChanged(Editable s) {  }
    };


    /**
     * Metodo encargado de realizar la busqueda de 'queries'
     */
    public void search()
    {
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(mSearchView.getWindowToken(), 0);
        }

        final String query = mSearchView.getText().toString();

        if (query.isEmpty())
        {
            Toast.makeText(getContext(), R.string.ingrese_una_direccion, Toast.LENGTH_SHORT).show();
            return;
        }

        mClient.autocomplete(query, new Callback<AutocompleteResult>() {
            @Override
            public void onResponse(Call<AutocompleteResult> call, Response<AutocompleteResult> response) {
                if (response.isSuccessful())
                {
                    mAdapter.clear();
                    if (mPlaceWebserviceListener!=null) {
                        if (!response.body().predictions.isEmpty()) {
                            String placeId = response.body().predictions.get(0).getPlaceId();
                            mClient.getLocation(placeId, new GooglePlaces.PlaceListener() {
                                @Override
                                public void onSuccess(LatLng latLng) {
                                    mPlaceWebserviceListener.onPlaceSelected(latLng);
                                }
                            });
                        }else{
                            Toast.makeText(getContext(), getContext().getString(R.string.no_se_encontraron_resultados_para)+query, Toast.LENGTH_SHORT).show();
                        }
                    }

                }
            }
            @Override
            public void onFailure(Call<AutocompleteResult> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }


    private void setClearVisibility(boolean empty, boolean characterLimit) {
        mRoot.findViewById(R.id.clear).setVisibility(empty?View.GONE:View.VISIBLE);
        if (empty || characterLimit) {
            mAdapter.clear();
        }
    }



    public interface PlaceWebserviceListener {
        void onPlaceSelected(LatLng latLng);
    }
}

