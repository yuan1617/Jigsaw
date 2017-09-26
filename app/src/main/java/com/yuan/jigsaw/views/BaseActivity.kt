package com.yuan.jigsaw.views

import android.Manifest
import android.annotation.TargetApi
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import java.util.HashMap

open class BaseActivity : AppCompatActivity()
{
    var screenWidth : Int = 0
    var screenHeight : Int = 0

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        val wm = this.windowManager
        screenWidth = wm.defaultDisplay.width
        screenHeight = wm.defaultDisplay.height
    }

    protected interface PermissionCallback
    {
        fun onSuccess()
        fun onFailure()
    }

    protected val permissionCallbacks = HashMap<Int, PermissionCallback>()
    protected var permissionRequestCodeSerial = 0
    @TargetApi(23) protected fun requestCameraPermission(permissions: String,callback: PermissionCallback)
    {
        if (Build.VERSION.SDK_INT >= 23)
        {
            if (checkSelfPermission(permissions) != PackageManager.PERMISSION_GRANTED)
            {
                val requestCode = permissionRequestCodeSerial
                permissionRequestCodeSerial += 1
                permissionCallbacks.put(requestCode, callback)
                requestPermissions(arrayOf(permissions), requestCode)
            } else
            {
                callback.onSuccess()
            }
        } else
        {
            callback.onSuccess()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray)
    {
        if (permissionCallbacks.containsKey(requestCode))
        {
            val callback = permissionCallbacks[requestCode]
            permissionCallbacks.remove(requestCode)
            var executed = false
            for (result in grantResults)
            {
                if (result != PackageManager.PERMISSION_GRANTED)
                {
                    executed = true
                    callback?.onFailure()
                }
            }
            if (!executed)
            {
                callback?.onSuccess()
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}