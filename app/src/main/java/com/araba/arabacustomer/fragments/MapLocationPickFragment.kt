package com.araba.arabacustomer.fragments

import android.annotation.SuppressLint
import android.app.Fragment
import android.content.Context
import android.location.*
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.araba.arabacustomer.R
import com.araba.arabacustomer.activitys.HomeActivity
import com.araba.arabacustomer.activitys.OtpActivity
import com.araba.arabacustomer.interfaces.DataInterface
import com.araba.arabacustomer.utility.FlagUtility
import com.araba.arabacustomer.utility.Utility
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import java.io.IOException
import java.util.*

/**
 * Created by rajneeshkumar on 10/31/17.
 */
class MapLocationPickFragment : Fragment(), OnMapReadyCallback {


    //    var locatedAddressName: TextView? = null
    var mapView: MapView? = null
    var useThisLocation: TextView? = null


    var googleMap: GoogleMap? = null
    var lat: Double = 0.toDouble()
    var lon: Double = 0.toDouble()
    var geocoder: Geocoder? = null
    var mAddress: Address? = null
    var mDataInterface: DataInterface? = null
    private var addresses: List<Address>? = null
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        var mView = inflater!!.inflate(R.layout.fargment_map_pic_location, container, false)
        getAllIds(mView)
        allClickes()


        geocoder = Geocoder(activity, Locale.getDefault())

        mapView?.onCreate(savedInstanceState)
        mapView?.getMapAsync(this)

        val dropPinView = ImageView(activity)
        dropPinView.setImageResource(R.drawable.pic_location)
        // Statically Set drop pin in center of screen
        val params = FrameLayout.LayoutParams(80, 80, Gravity.CENTER)
        val density = resources.displayMetrics.density
        params.bottomMargin = (12 * density).toInt()
        dropPinView.layoutParams = params
        mapView?.addView(dropPinView)


        return mView
    }

    fun getAllIds(mView: View) {
        mDataInterface = activity as DataInterface
        mapView = mView.findViewById(R.id.map)
//        locatedAddressName = mView.findViewById(R.id.locatedAddressName)
        useThisLocation = mView.findViewById(R.id.useThisLocation)
    }

    fun allClickes() {
        useThisLocation?.setOnClickListener {
            if (mAddress != null) {
                mDataInterface?.data(FlagUtility.USE_THIS_LOCATION_CLICK, mAddress!!, 0)
            }
        }
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap?) {
        this.googleMap = googleMap
        geocoder = Geocoder(activity)
        googleMap?.setMyLocationEnabled(true)

        moveCameraOnCurrentLocation()

        googleMap?.setOnCameraMoveListener(GoogleMap.OnCameraMoveListener {
            lat = googleMap.getCameraPosition().target.latitude
            lon = googleMap.getCameraPosition().target.longitude

//            mAddress = Utility.getAddressNameFromLatLong(activity, lat as Double, lon)
//            locatedAddressName?.text = mAddress?.getAddressLine(0)

            GetAddressName().execute()

        })
    }

    fun moveCameraOnCurrentLocation() {
        var mLocationManager = activity.getSystemService(Context.LOCATION_SERVICE) as LocationManager?
//        var mLocation = mLocationManager?.getLastKnownLocation(LocationManager.GPS_PROVIDER)

        val locationListener: LocationListener = object : LocationListener {
            override fun onLocationChanged(location: Location) {
                googleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(location?.latitude as Double, location?.longitude), 15f))
            }

            override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
            override fun onProviderEnabled(provider: String) {}
            override fun onProviderDisabled(provider: String) {}
        }
        mLocationManager?.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0L, 0f, locationListener)


    }

    override fun onResume() {
        mapView?.onResume()
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView?.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView?.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView?.onLowMemory()
    }

    inner class GetAddressName : AsyncTask<Any, Address, Address>() {

        override fun onPreExecute() {
            super.onPreExecute()
        }

        override fun doInBackground(p0: Array<Any>): Address? {
            addresses = geocoder?.getFromLocation(lat, lon, 1)
            if (addresses?.size!! > 0) {
                return addresses?.get(0);
            } else {
                return null
            }
        }

        override fun onPostExecute(result: Address?) {
            super.onPostExecute(result)

            if (result == null) {
//                locatedAddressName?.text = "NO location found !"
                mDataInterface?.data(FlagUtility.ADDRESS, "NO location found !", 0)
            } else {
                mAddress = result as Address
//                locatedAddressName?.text = mAddress?.getAddressLine(0)
                mDataInterface?.data(FlagUtility.ADDRESS, mAddress?.getAddressLine(0).toString(), 0)

            }

        }

    }


}