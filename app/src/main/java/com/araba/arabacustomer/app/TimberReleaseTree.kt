package com.araba.arabacustomer.app

import android.util.Log
import timber.log.Timber



/**
 * Created by Rajneesh on 23-Aug-17.
 */
class TimberReleaseTree : Timber.Tree() {
    private val MAX_LOG_LENGTH = 4000
    override fun isLoggable(priority: Int): Boolean {
        if (priority == Log.VERBOSE || priority == Log.DEBUG || priority == Log.INFO) {
            return false
        } else {
            return true
        }
    }

    override fun log(priority: Int, tag: String, message: String, t: Throwable?) {
        if (isLoggable(priority)) {
            // Report catch exception to crashlytics (or what ever crash library you use)
            if (priority == Log.ERROR && t != null) {
            }
            if (message.length < MAX_LOG_LENGTH) {
                if (priority == Log.ASSERT) {
                    Log.wtf(tag, message)
                } else {
                    Log.println(priority, tag, message)
                }
                return
            }
            //Split by line then ensure each line can fit into log maximum length.
            var i = 0 as Int
            val mLength = message.length
            while (i < mLength) {
                var newLine = message.indexOf('\n', i)
                newLine = if (newLine != -1) newLine else mLength
                do {
                    i = MAX_LOG_LENGTH
                    val end = Math.max(newLine, i)
                    val part = message.substring(i, end)
                    if (priority == Log.ASSERT) {
                        Log.wtf(tag, part)
                    } else {
                        Log.println(priority, tag, part)
                    }
                    i = end
                } while (i < newLine)
                i++
            }
        }
    }
}