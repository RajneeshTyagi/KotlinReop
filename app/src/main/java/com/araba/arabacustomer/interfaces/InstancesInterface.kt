package com.araba.arabacustomer.interfaces

import com.araba.arabacustomer.common.app.CoreApplication
import com.araba.arabacustomer.network.CustomRetrofit
import com.araba.arabacustomer.network.NetworkApiIInterface
import com.araba.arabacustomer.sharedpreferences.MySharedPreferences
import com.araba.arabacustomer.sharedpreferences.UnClearSharedPreference

/**
 * Created by rajneeshkumar on 10/24/17.
 */
interface InstancesInterface {
    companion object {
        var mCoreApplication: CoreApplication
            get() = CoreApplication.getInstance()
            set(v) {}
        val mNetworkApiIInterface: NetworkApiIInterface
            get() = CustomRetrofit.getApiInterfaceInstance()
        var mySharedPreferences: MySharedPreferences
            get() = MySharedPreferences.getInstance(mCoreApplication)
            set(v) {}
        var unClearSharedPreference: UnClearSharedPreference
            get() = UnClearSharedPreference.getInstance(mCoreApplication)
            set(v) {}
    }

}