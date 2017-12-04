package com.araba.arabacustomer.activitys

import android.app.Activity
import android.content.Intent
import android.location.Address
import android.os.Bundle
import com.araba.arabacustomer.R
import com.araba.arabacustomer.activity.BaseActivity_Permission
import com.araba.arabacustomer.interfaces.DataInterface
import com.araba.arabacustomer.utility.FlagUtility
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import io.reactivex.disposables.Disposable

/**
 * Created by rajneeshkumar on 10/31/17.
 */
class PickLocationActivity : BaseActivity_Permission(), DataInterface {


    companion object {
        fun start(mActivity: Activity) {
            val mIntent = Intent(mActivity, PickLocationActivity::class.java)
            mActivity.startActivity(mIntent)
        }
    }

    override fun getAllIds() {
    }

    override fun setContentView() {
        initializeView(R.layout.activity_map_pick_location)
    }

    override fun initView(savedInstanceState: Bundle?) {
        showToolBar()
        showToolBarBack()
    }

    override fun data(flag: String, value: Any?, position: Int) {
        if (FlagUtility.ADDRESS.equals(flag)) {
            setTitle(value as String)
        }
        if (FlagUtility.USE_THIS_LOCATION_CLICK.equals(flag)) {
            var mAddress = value as Address
            if (mAddress != null) {
                HomeActivity.start(this)
            }
//            setTitle(value as String)
        }
    }

    override fun permissionGranted(permission: String, requestCode: Int) {
    }

    override fun permissionDenied(permission: String, requestCode: Int) {
    }

    override fun onRequestPermissionsResultCustom(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
    }

    override fun onNext(value: Any?) {
    }

    override fun onComplete() {
    }

    override fun onSubscribe(d: Disposable?) {
    }

    override fun onError(e: Throwable?) {
    }
}