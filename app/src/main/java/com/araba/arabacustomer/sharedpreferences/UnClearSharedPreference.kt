package com.araba.arabacustomer.sharedpreferences

import android.content.Context
import android.content.SharedPreferences

/**
 * Created by rajneeshkumar on 11/2/17.
 */
class UnClearSharedPreference {

    companion object {
        var mUnClearSharedPreference: UnClearSharedPreference? = null

        fun getInstance(context: Context): UnClearSharedPreference {
            if (mUnClearSharedPreference == null) {
                mUnClearSharedPreference = UnClearSharedPreference(context)
            }
            return mUnClearSharedPreference as UnClearSharedPreference
        }
    }

    var prefs: SharedPreferences? = null
    var editor: SharedPreferences.Editor? = null

    private constructor(context: Context?) {
        prefs = context!!.getSharedPreferences("MyUnClearPreference", Context.MODE_PRIVATE)
        editor = prefs?.edit()
    }

    fun setStringValue(key: String, value: String) {
        editor?.putString(key, value)
        editor?.apply()
    }

    fun getStringValue(key: String): String {
        return prefs?.getString(key, "")!!
    }
}