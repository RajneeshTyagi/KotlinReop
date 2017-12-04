package com.araba.arabacustomer.common.app

import android.app.Application
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import com.araba.arabacustomer.app.TimberReleaseTree
import com.araba.arabacustomer.app.TypefaceUtil
import timber.log.BuildConfig
import timber.log.Timber

/**
 * Created by Rajneesh on 22-Aug-17.
 */
class CoreApplication : Application() {

    companion object {
        var coreApplication: CoreApplication? = null
        fun getInstance(): CoreApplication {
            return coreApplication as CoreApplication
        }
    }

    override fun onCreate() {
        // TODO Auto-generated method stub
        super.onCreate()
        coreApplication = this
        TypefaceUtil.overrideFont(applicationContext, "serif", "fonts/Roboto-Regular.ttf") // font from assets: "assets/fonts/Roboto-Regular.ttf
        //        initialize();

        /*
        * for Log Print Library
        * if will perforce when
        */
        if (!BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(TimberReleaseTree())
        }


    }

    private var versionCode: Int = 0
    private var versionName: String? = null

    fun getVersionCode(): Int {
        fillVersionData()
        return versionCode
    }

    fun getVersionName(): String {
        fillVersionData()
        return versionName as String
    }

    private fun fillVersionData() {
        if (versionCode == 0 || versionName == null) {
            var pInfo: PackageInfo? = null
            try {
                pInfo = packageManager.getPackageInfo(packageName, 0)
                versionCode = pInfo!!.versionCode
                versionName = pInfo.versionName
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
            }

        }
    }

}