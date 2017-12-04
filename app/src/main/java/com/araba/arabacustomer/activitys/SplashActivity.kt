package com.araba.arabacustomer.activitys

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import com.araba.arabacustomer.R
import com.araba.arabacustomer.activity.BaseActivity
import com.araba.arabacustomer.interfaces.InstancesInterface
import com.araba.arabacustomer.interfaces.InstancesInterface.Companion.mySharedPreferences
import com.araba.arabacustomer.utility.SharedPreferenceKeys
import timber.log.Timber

class SplashActivity : AppCompatActivity(), InstancesInterface {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler().postDelayed(Runnable {
            if (mySharedPreferences.getStringValue(SharedPreferenceKeys.CLIENT_TOKEN).equals("")) {
                ChooseLanguageActivity.start(this@SplashActivity)
            } else {
                ShowCurrentLocationActivity.start(this)
            }
        }, 4000)
    }


}
