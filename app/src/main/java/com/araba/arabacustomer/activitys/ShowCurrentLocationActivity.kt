package com.araba.arabacustomer.activitys

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.location.*
import android.os.Bundle
import android.widget.TextView
import com.araba.arabacustomer.R
import com.araba.arabacustomer.activity.BaseActivity
import com.araba.arabacustomer.utility.Utility
import io.reactivex.disposables.Disposable
import java.util.*


/**
 * Created by rajneeshkumar on 11/6/17.
 */
class ShowCurrentLocationActivity : BaseActivity() {


    companion object {
        fun start(mActivity: Activity) {
            val mIntent = Intent(mActivity, ShowCurrentLocationActivity::class.java)
            mActivity.startActivity(mIntent)
            mActivity.finish()
        }
    }

    var currentLocation: TextView? = null
    var useCurrentLocation: TextView? = null
    var useDifferetLocation: TextView? = null
    //    var geocoder: Geocoder? = null
//    var addresses: List<Address>? = null
    var mAddress: Address? = null

    override fun setContentView() {
        initializeView(R.layout.activity_show_current_location)
    }

    override fun getAllIds() {
        currentLocation = findViewById(R.id.currentLocation)
        useCurrentLocation = findViewById(R.id.useCurrentLocation)
        useDifferetLocation = findViewById(R.id.useDifferetLocation)
    }

    override fun initView(savedInstanceState: Bundle?) {

        var mLocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager?
        var mLocation = mLocationManager?.getLastKnownLocation(LocationManager.GPS_PROVIDER)

        mAddress = Utility.getAddressNameFromLatLong(this, mLocation?.latitude as Double, mLocation?.longitude)
        currentLocation?.text = mAddress?.getAddressLine(0)

        useCurrentLocation?.setOnClickListener { HomeActivity.start(this) }
        useDifferetLocation?.setOnClickListener {
            PickLocationActivity.start(this)
        }
    }


    override fun onNext(value: Any?) {
    }

    override fun onSubscribe(d: Disposable?) {
    }

    override fun onComplete() {
    }

    override fun onError(e: Throwable?) {
    }
}