package com.araba.arabacustomer.activity

import android.os.Bundle
import com.afollestad.assent.Assent
import com.afollestad.assent.AssentCallback

/**
 * Created by Rajneesh on 23-Aug-17.
 */
abstract class BaseActivity_Permission : BaseActivity() {

    fun grantPermission(permission: String, requestCode: Int) {
        if (!Assent.isPermissionGranted(permission)) {
            // The if statement checks if the permission has already been granted before
            Assent.requestPermissions(AssentCallback { result ->
                val isGranted = result.isGranted(permission)
                if (isGranted) {
                    permissionGranted(permission, requestCode)
                } else {
                    permissionDenied(permission, requestCode)
                }
            }, requestCode, permission)
        } else {
            permissionGranted(permission, requestCode)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        // Updates the activity when the Activity is first created
        // That way you can request permissions from within onCreate()
        Assent.setActivity(this, this)
        super.onCreate(savedInstanceState)

    }

    override fun onResume() {
        // Updates the activity every time the Activity becomes visible again
        Assent.setActivity(this, this)

        super.onResume()
    }

    override fun onPause() {
        // Cleans up references of the Activity to avoid memory leaks
        if (isFinishing)
            Assent.setActivity(this, null)
        super.onPause()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        // Lets Assent handle permission results, and contact your callbacks
        Assent.handleResult(permissions, grantResults)
        onRequestPermissionsResultCustom(requestCode, permissions, grantResults)
    }

    abstract fun permissionGranted(permission: String, requestCode: Int)

    abstract fun permissionDenied(permission: String, requestCode: Int)

    abstract fun onRequestPermissionsResultCustom(requestCode: Int, permissions: Array<String>, grantResults: IntArray)

    val REQUEST_CODE_WRITE_EXTERNAL_STORAGE = 101
    val PICK_IMAGE_REQUEST = 102
    val PICK_LOCATION = 103


}