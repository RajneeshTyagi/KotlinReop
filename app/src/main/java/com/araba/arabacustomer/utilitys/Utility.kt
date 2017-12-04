package com.araba.arabacustomer.utility

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.graphics.Bitmap
import android.location.Address
import android.location.Geocoder
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Build
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.support.v7.app.AlertDialog
import android.text.TextUtils
import android.util.Base64
import android.util.DisplayMetrics
import android.widget.Toast
import com.araba.arabacustomer.R
import org.json.JSONException
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.util.*
import java.util.regex.Pattern

/**
 * Created by Rajneesh on 23-Aug-17.
 */
class Utility {
    /**
     * The progress dialog.
     */
    companion object {
        var progressDialog: ProgressDialog? = null


        fun showAlertDialog(mActivity: Activity?, title: String, message: String) {
            var title = title
            if (mActivity != null && mActivity is Activity) {
                val alertDialog: AlertDialog
                alertDialog = AlertDialog.Builder(mActivity, R.style.Theme_AppCompat_Light_Dialog_Alert).create()
                title = if (TextUtils.isEmpty(title)) mActivity.resources.getString(R.string.app_name) else title
                // Setting Dialog Title
                alertDialog.setTitle(title)
                // Setting Dialog Message
                alertDialog.setMessage(message)
                // Setting alert dialog icon
                // alertDialog.setIcon((status) ? R.drawable.success :
                // R.drawable.fail);
                // Setting OK Button
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK") { dialog, which -> alertDialog.dismiss() }
                // Showing Alert Message
                alertDialog.show()
            }
        }

        fun showAlertDialog(context: Context?, title: String, message: String, listener: DialogInterface.OnClickListener): AlertDialog? {
            var title = title
            if (context != null && context is Activity && !context.isFinishing) {
                val alertDialog: AlertDialog
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                    alertDialog = AlertDialog.Builder(context, R.style.Theme_AppCompat_Light_Dialog_Alert).create()
                } else {
                    alertDialog = AlertDialog.Builder(context).create()
                }
                title = if (TextUtils.isEmpty(title)) context.resources.getString(R.string.app_name) else title
                // Setting Dialog Title
                alertDialog.setTitle(title)
                // Setting Dialog Message
                alertDialog.setMessage(message)

                // Setting alert dialog icon
                // alertDialog.setIcon((status) ? R.drawable.success :
                // R.drawable.fail);

                // Setting Cancel Button
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel", listener)

                // Setting OKAY Button
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Okay", listener)

                // Showing Alert Message
                alertDialog.show()

                return alertDialog
            }
            return null
        }

        fun showProgressDialog(context: Context?) {
//            var title = title
//            var message = message
            hideProgressDialog()
            if (context != null && context is Activity) {
//                title = if (TextUtils.isEmpty(title)) context.resources.getString(R.string.app_name) else title
//                message = if (TextUtils.isEmpty(message)) "Loading. Please wait.." else message
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
                    progressDialog = ProgressDialog(context)
                } else {
                    progressDialog = ProgressDialog(context, ProgressDialog.THEME_HOLO_LIGHT)
                }
                progressDialog!!.setTitle(context.resources.getString(R.string.app_name))
                progressDialog!!.setMessage(context.resources.getString(R.string.please_wait))
                progressDialog!!.setProgressStyle(ProgressDialog.STYLE_SPINNER)
//                if (isCancelable.size > 0) {
                progressDialog!!.setCancelable(false)
//                }
                progressDialog!!.show()
            }
        }

        /**
         * Function to hide progress Dialog.
         * @author Rajneesh
         */
        fun hideProgressDialog() {
            try {
                if (progressDialog != null) {
                    if (progressDialog!!.isShowing) {
                        progressDialog!!.hide()
                        progressDialog!!.dismiss()
                    }
                    progressDialog = null
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }


        // validating email id
        fun isValidEmail(email: String): Boolean {
            val EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
            val pattern = Pattern.compile(EMAIL_PATTERN)
            val matcher = pattern.matcher(email)
            return matcher.matches()
        }

        fun CheckConnectivity(mActivity: Activity): Boolean {
            var temp = false
            try {
                val connectivityManager = mActivity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                val wifiInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                val mobileInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)

                if (wifiInfo.isConnected || mobileInfo.isConnected) {
                    temp = true
                } else {
                    temp = false
                    Toast.makeText(mActivity, "Check Your Network Connection", Toast.LENGTH_LONG).show()
                    showAlertDialog(mActivity, mActivity.resources.getString(R.string.app_name), "Please Check Your Network Connection\nTry Again")
                }
            } catch (e: Exception) {
                //             System.out.println("CheckConnectivity Exception: " + e.getMessage());
            }

            return temp
        }

        //Get Map from Object
        fun getMapFromJsonObj(jsonObject: JSONObject?): Map<String, String> {
            val map = HashMap<String, String>()
            if (jsonObject != null) {
                val keys = jsonObject.keys()
                while (keys.hasNext()) {
                    val key = keys.next()
                    var value: String? = null
                    try {
                        value = jsonObject.getString(key)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }

                    map.put(key, value!!)
                }
            }
            return map
        }


        fun isTablet(context: Context): Boolean {
            if (context.resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK == Configuration.SCREENLAYOUT_SIZE_LARGE
                    ||
                    context.resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK == Configuration.SCREENLAYOUT_SIZE_XLARGE) {
                // on a large screen device ...

                return true

            }
            return false
        }


        fun getScreenDensity(context: Activity): String {
            var dpi = ""
            val metrics = DisplayMetrics()
            context.windowManager.defaultDisplay.getMetrics(metrics)
            when (metrics.densityDpi) {
                DisplayMetrics.DENSITY_LOW -> dpi = "ldpi"
                DisplayMetrics.DENSITY_MEDIUM -> dpi = "mdpi"
                DisplayMetrics.DENSITY_HIGH -> dpi = "hdpi"
                DisplayMetrics.DENSITY_XHIGH -> dpi = "xhdpi"
                DisplayMetrics.DENSITY_XXHIGH -> dpi = "xxhdpi"
                DisplayMetrics.DENSITY_XXXHIGH -> dpi = "xxxhdpi"
            }
            return dpi
        }

        /*
        * get Device name
        */
        fun getDeviceName(): String {
            val manufacturer = Build.MANUFACTURER
            val model = Build.MODEL
            if (model.startsWith(manufacturer)) {
                return capitalize(model)
            } else {
                return capitalize(manufacturer) + " " + model
            }
        }


        private fun capitalize(s: String?): String {
            if (s == null || s.length == 0) {
                return ""
            }
            val first = s[0]
            if (Character.isUpperCase(first)) {
                return s
            } else {
                return Character.toUpperCase(first) + s.substring(1)
            }
        }

        fun hasGPS(mContext: Context): Boolean {
            val packageManager = mContext.packageManager
            val hasGPS = packageManager.hasSystemFeature(PackageManager.FEATURE_LOCATION_GPS)
            return hasGPS
            /*
            LocationManager manager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
            return manager.getAllProviders().size() > 0; */
        }


        @SuppressLint("NewApi")
        fun getRealPathFromCameraUri(context: Context, uri: Uri): String {
            var filePath = ""
            val wholeID = DocumentsContract.getDocumentId(uri)

            // Split at colon, use second item in the array
            val id = wholeID.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[1]

            val column = arrayOf(MediaStore.Images.Media.DATA)

            // where id is equal to
            val sel = MediaStore.Images.Media._ID + "=?"

            val cursor = context.contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    column, sel, arrayOf(id), null)

            val columnIndex = cursor!!.getColumnIndex(column[0])

            if (cursor.moveToFirst()) {
                filePath = cursor.getString(columnIndex)
            }
            cursor.close()
            return filePath
        }

        fun BitMapToString(bitmap: Bitmap): String {
            val baos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
            val b = baos.toByteArray()
            val temp = Base64.encodeToString(b, Base64.DEFAULT)
            return temp
        }

        fun getAddressNameFromLatLong(mActivity: Activity, latitude: Double, longitude: Double): Address? {
            var geocoder = Geocoder(mActivity, Locale.getDefault())
            var addresses: List<Address>? = geocoder?.getFromLocation(latitude, longitude, 1)
            return addresses?.get(0)
        }

    }

}