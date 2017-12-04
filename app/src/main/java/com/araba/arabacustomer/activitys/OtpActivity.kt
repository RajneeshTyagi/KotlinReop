package com.araba.arabacustomer.activitys

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.araba.arabacustomer.R
import com.araba.arabacustomer.activity.BaseActivity_Permission
import com.araba.arabacustomer.interfaces.InstancesInterface.Companion.mySharedPreferences
import com.araba.arabacustomer.models.GetOtpResponseModel
import com.araba.arabacustomer.models.MobileOtpSendModel
import com.araba.arabacustomer.models.MobileSendModel
import com.araba.arabacustomer.models.SendOtpResponseModel
import com.araba.arabacustomer.network.NetworkCalls
import com.araba.arabacustomer.utility.SharedPreferenceKeys
import com.araba.arabacustomer.utility.Utility
import io.reactivex.disposables.Disposable

/**
 * Created by rajneeshkumar on 10/31/17.
 */
class OtpActivity : BaseActivity_Permission() {


    companion object {
        val MOBILE_NO_KEY = "mobile_no_key"
        val OTP_KEY = "otp"
        fun start(mActivity: Activity, mobileNo: String, otp: String) {
            val mIntent = Intent(mActivity, OtpActivity::class.java)
            mIntent.putExtra(MOBILE_NO_KEY, mobileNo)
            mIntent.putExtra(OTP_KEY, otp)
            mActivity.startActivity(mIntent)

            mActivity.finish()
        }
    }

    var progressBar: ProgressBar? = null
    var otp: EditText? = null
    var reSend: Button? = null
    var send: Button? = null

    override fun setContentView() {
        initializeView(R.layout.activity_otp)
    }

    override fun initView(savedInstanceState: Bundle?) {
        getAllIds()
        allClickes()
        otp?.setText(intent.getStringExtra(OTP_KEY))
//        otp?.text=intent.getStringExtra(OTP_KEY).toString()

    }

    override fun getAllIds() {
        progressBar = findViewById(R.id.progressBar)
        otp = findViewById(R.id.otp)
        reSend = findViewById(R.id.reSend)
        send = findViewById(R.id.send)
    }

    fun allClickes() {
        reSend?.setOnClickListener {
            Utility.showProgressDialog(this)
            NetworkCalls.getOtp(this, MobileSendModel(intent.getStringExtra(MOBILE_NO_KEY)))

        }
        send?.setOnClickListener {
            grantPermission(Manifest.permission.ACCESS_FINE_LOCATION, PICK_LOCATION)
        }
    }

    override fun permissionGranted(permission: String, requestCode: Int) {

        var OtpValue = otp?.text.toString()
        if (OtpValue.equals("")) {
            otp?.error = resources.getString(R.string.please_enter_otp)
        } else {
            Utility.showProgressDialog(this)
            NetworkCalls.sendOtp(this, MobileOtpSendModel(intent.getStringExtra(MOBILE_NO_KEY), OtpValue))
        }
    }

    override fun permissionDenied(permission: String, requestCode: Int) {
        grantPermission(Manifest.permission.ACCESS_FINE_LOCATION, PICK_LOCATION)
    }

    override fun onRequestPermissionsResultCustom(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
    }

    override fun onSubscribe(d: Disposable?) {
    }

    override fun onError(e: Throwable?) {
        Utility.hideProgressDialog()
    }

    override fun onNext(value: Any?) {
        Utility.hideProgressDialog()
        if (value is GetOtpResponseModel) {
            var mGetOtpResponseModel = value
            otp?.setText(mGetOtpResponseModel.data.OTP)
            Toast.makeText(this, mGetOtpResponseModel.message, Toast.LENGTH_LONG).show()
        } else if (value is SendOtpResponseModel) {
            var mSendOtpResponseModel = value
            otp?.setText(mSendOtpResponseModel.data.OTP)
            mySharedPreferences.setStringValue(SharedPreferenceKeys.CLIENT_TOKEN, mSendOtpResponseModel.data.device_token)
            ShowCurrentLocationActivity.start(this)
            Toast.makeText(this, mSendOtpResponseModel.data.device_token, Toast.LENGTH_LONG).show()
        }
    }

    override fun onComplete() {
        Utility.hideProgressDialog()

    }
}