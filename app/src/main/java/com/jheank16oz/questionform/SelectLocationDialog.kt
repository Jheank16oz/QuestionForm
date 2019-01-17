package com.jheank16oz.questionform

import android.app.Activity
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.layout_select_location_dialog.view.*


/**
 *
 *  <p>SelectLocationDialog</p>
 */

class SelectLocationDialog : DialogFragment(), PlaceAutoCompleteWebservice.PlaceWebserviceListener, OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{

    private var mMap: GoogleMap? = null
    private var mGoogleApiClient: GoogleApiClient? = null
    private lateinit var mMapFragment:SupportMapFragment
    private lateinit var locationText:TextView
    private lateinit var viewFragment:View
    private var listener:SelectLatLngListener? = null
    private var  initialLatLng:LatLng? = null

    override fun onPlaceSelected(latLng: LatLng?) {
        updateCamera(latLng)
        hideKeyboardFrom(viewFragment)
    }

    private fun updateCamera(latLng: LatLng?) {
        latLng?.let {
            val cap = CameraPosition(latLng,
                    17f, 0f, 0f)
            mMap?.animateCamera(CameraUpdateFactory.newCameraPosition(cap))
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialogStyle)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        viewFragment = inflater.inflate(R.layout.layout_select_location_dialog, container, false)

        val mSearchView = viewFragment.autocomplete
        mSearchView?.initializeAutocomplete(4, "AIzaSyAcBwJsEHGbxId6cCpR1TcAtWSED6Kuvo0", this)
        viewFragment.toolbar?.setNavigationIcon(R.drawable.ic_clear_white_24dp)
        locationText = viewFragment.location
        viewFragment.toolbar?.title = "Busqueda de dirección"
        viewFragment.toolbar?.setNavigationOnClickListener {
            dismiss()
        }

        initializeGoogleApiClient()

        mMapFragment = SupportMapFragment()
        val transaction = childFragmentManager.beginTransaction()
        transaction.add(R.id.map, mMapFragment).commit()
        viewFragment.select.setOnClickListener {
            listener?.selected(mMap?.cameraPosition?.target)
            dismiss()
        }


        return viewFragment
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        mMap = googleMap

        mMap?.setOnCameraIdleListener {
            locationText.text = "${mMap?.cameraPosition?.target}"
        }

        updateCamera(initialLatLng)
    }

    override fun onConnected(p0: Bundle?) {
        mMapFragment.getMapAsync(this)

    }

    override fun onConnectionSuspended(i: Int) {
    }

    override fun onConnectionFailed(connectionResult: ConnectionResult) {

    }

    /**
     * Se encarga de inicializar Google api client
     * y agregar las configuraciones necesarias
     */
    private fun initializeGoogleApiClient() {
        if (context != null) {
            if (mGoogleApiClient == null) {
                mGoogleApiClient = GoogleApiClient.Builder(context!!)
                        .addApi(LocationServices.API)
                        .addConnectionCallbacks(this)
                        .addOnConnectionFailedListener(this)
                        .build()
            }
            mGoogleApiClient?.connect()
        }
    }


    override fun onStart() {
        super.onStart()
        val dialog = dialog
        if (dialog != null) {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.MATCH_PARENT
            dialog.window.setLayout(width, height)
        }
    }

    companion object {
        const val TAG = "FullScreenDialog"
    }

    private fun hideKeyboardFrom(view: View) {
        val imm = context?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    interface SelectLatLngListener{
        fun selected(location:LatLng?)
    }

    fun setSelectLatLngListener(listener:SelectLatLngListener?){
        this.listener = listener
    }

    fun initialLatLng(location: LatLng?) {
        initialLatLng = location
    }

}