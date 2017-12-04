package com.araba.arabacustomer.activitys

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.*
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.araba.arabacustomer.R
import com.araba.arabacustomer.activity.BaseActivity
import com.araba.arabacustomer.interfaces.InstancesInterface.Companion.mNetworkApiIInterface
import com.araba.arabacustomer.models.CountryResponseModel
import com.araba.arabacustomer.models.GetOtpResponseModel
import com.araba.arabacustomer.models.MobileSendModel
import com.araba.arabacustomer.network.NetworkCalls
import com.araba.arabacustomer.utility.Utility
import io.reactivex.disposables.Disposable

/**
 * Created by rajneeshkumar on 10/30/17.
 */
class ChooseLanguageActivity : BaseActivity() {


    companion object {
        fun start(mActivity: Activity) {
            val mIntent = Intent(mActivity, ChooseLanguageActivity::class.java)
            mActivity.startActivity(mIntent)
            mActivity.finish()
        }
    }

//    @BindView(R.id.rbEnglish)
//    lateinit var rbEnglish: RadioButton
//    @BindView(R.id.rbArabic)
//    lateinit var rbArabic: RadioButton
//    @BindView(R.id.sendOtp)
//    lateinit var sendOtp: Button

    var rbEnglish: RadioButton? = null
    var rbArabic: RadioButton? = null
    var countryCode: EditText? = null
    var mobileNo: EditText? = null
    var sendOtp: Button? = null
    var mobileNoValue = ""

    override fun setContentView() {
        initializeView(R.layout.activity_choose_language)
//        ButterKnife.bind(this)

    }

    override fun initView(savedInstanceState: Bundle?) {
        getAllIds()
        allClickes()

        countryCode?.setText("+91")
        countryCode?.isEnabled = false
    }

    override fun getAllIds() {
        rbEnglish = findViewById(R.id.rbEnglish)
        rbArabic = findViewById(R.id.rbArabic)
        countryCode = findViewById(R.id.countryCode)
        mobileNo = findViewById(R.id.mobileNo)
        sendOtp = findViewById(R.id.sendOtp)
    }

    fun allClickes() {
        sendOtp?.setOnClickListener {
            mobileNoValue = mobileNo?.text.toString()
            if (mobileNoValue.equals("")) {
                mobileNo?.error = resources.getString(R.string.please_enter_mobile_no)
            } else {
                Utility.showProgressDialog(this)
                NetworkCalls.getOtp(this, MobileSendModel(mobileNoValue))
            }

        }
    }
//    @OnClick(R.id.sendOtp)
//    internal fun sendOtpClicked() {
//        System.out.print("Hello")
//        Toast.makeText(this, "Hello, views!", Toast.LENGTH_LONG).show()
//    }

    override fun onComplete() {
        Utility.hideProgressDialog()
    }

    override fun onNext(value: Any?) {
        Utility.hideProgressDialog()
        if (value is CountryResponseModel) {
            var mCountryResponseModel = value
            Toast.makeText(this, mCountryResponseModel.message, Toast.LENGTH_LONG).show()
        } else if (value is GetOtpResponseModel) {
            var mGetOtpResponseModel = value
            OtpActivity.start(this, mobileNoValue, mGetOtpResponseModel.data.OTP)
            Toast.makeText(this, mGetOtpResponseModel.message, Toast.LENGTH_LONG).show()
        }
    }

    override fun onError(e: Throwable?) {
        Utility.hideProgressDialog()
    }

    override fun onSubscribe(d: Disposable?) {
    }
}