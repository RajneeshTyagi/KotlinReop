package com.araba.arabacustomer.network

import android.content.Context
import com.araba.arabacustomer.activitys.HomeActivity
import com.araba.arabacustomer.interfaces.InstancesInterface
import com.araba.arabacustomer.interfaces.InstancesInterface.Companion.mNetworkApiIInterface
import com.araba.arabacustomer.models.CountryResponseModel
import com.araba.arabacustomer.models.MobileOtpSendModel
import com.araba.arabacustomer.models.MobileSendModel
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.security.AccessControlContext

/**
 * Created by rajneeshkumar on 11/1/17.
 */
class NetworkCalls : InstancesInterface {
    companion object {
        /**
         * method to call login api
         */
        fun getCountryList(context: Context) {
            mNetworkApiIInterface.getCountryList()
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .take(1)
                    .subscribe(context as Observer<Any>)

        }

        fun getOtp(context: Context, mMobileSendModel: MobileSendModel) {
            mNetworkApiIInterface.getOtp(mMobileSendModel)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .take(1)
                    .subscribe(context as Observer<Any>)

        }

        fun sendOtp(context: Context, mMobileOtpSendModel: MobileOtpSendModel) {
            mNetworkApiIInterface.sendOtp(mMobileOtpSendModel)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .take(1)
                    .subscribe(context as Observer<Any>)

        }

        fun getCategoryList(context: Context) {
            mNetworkApiIInterface.getCategoryList("2")
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .take(1)
                    .subscribe(context as Observer<Any>)

        }


    }
}

