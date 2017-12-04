package com.araba.arabacustomer.sharedpreferences

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences

/**
 * Created by rajneeshkumar on 11/2/17.
 */
class MySharedPreferences {

    companion object {

        var mMySharedPreferences: MySharedPreferences? = null

        public fun getInstance(context: Context): MySharedPreferences {
            if (mMySharedPreferences == null) {
                mMySharedPreferences = MySharedPreferences(context)
            }
            return mMySharedPreferences as MySharedPreferences
        }
    }

    private var prefs: SharedPreferences? = null
    private var editor: SharedPreferences.Editor? = null

    private constructor(context: Context?) {
        prefs = context!!.getSharedPreferences("MyPreference", Context.MODE_PRIVATE)
        editor = prefs?.edit()
    }

    fun setStringValue(key: String, value: String) {
        editor?.putString(key, value)
        editor?.apply()
    }

    fun getStringValue(key: String): String {
        return prefs?.getString(key, "")!!
    }

    fun setBooleanValue(key: String, value: Boolean?) {
        editor?.putBoolean(key, value!!)
        editor?.apply()
    }

    fun getBooleanValue(key: String): Boolean? {
        return prefs?.getBoolean(key, false)!!
    }

    fun setIntegerValue(key: String, value: Int) {
        editor?.putInt(key, value)
        editor?.apply()
    }

    fun getIntegerValue(key: String): Int {
        return prefs?.getInt(key, 0)!!
    }

    fun clearMyPreference() {
        editor?.clear()
        editor?.apply()
    }


}