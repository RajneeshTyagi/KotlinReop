package com.araba.arabacustomer.app

import android.content.Context
import android.graphics.Typeface
import timber.log.Timber

/**
 * Created by Rajneesh on 22-Aug-17.
 */
class TypefaceUtil {
    /**
     * Using reflection to override default typeface
     * NOTICE: DO NOT FORGET TO SET TYPEFACE FOR APP THEME AS DEFAULT TYPEFACE WHICH WILL BE OVERRIDDEN

     * @param context                    to work with assets
     * *
     * @param defaultFontNameToOverride  for example "monospace"
     * *
     * @param customFontFileNameInAssets file name of the font from assets
     *
     */
    companion object {

        fun overrideFont(context: Context, defaultFontNameToOverride: String, customFontFileNameInAssets: String) {
            try {
                val customFontTypeface = Typeface.createFromAsset(context.assets, customFontFileNameInAssets)

                val defaultFontTypefaceField = Typeface::class.java.getDeclaredField(defaultFontNameToOverride)
                defaultFontTypefaceField.isAccessible = true
                defaultFontTypefaceField.set(null, customFontTypeface)
            } catch (e: Exception) {
                Timber.e("Can not set custom font $customFontFileNameInAssets instead of $defaultFontNameToOverride")
            }

        }
    }
}