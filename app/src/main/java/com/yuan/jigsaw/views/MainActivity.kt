package com.yuan.jigsaw.views

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.yuan.jigsaw.R

class MainActivity : BaseActivity()
{

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            requestCameraPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE, object : PermissionCallback
            {
                override fun onSuccess()
                {
                    Toast.makeText(applicationContext, "权限受理成功", Toast.LENGTH_SHORT).show()
                }

                override fun onFailure()
                {
                    finish()
                }
            })
        }
    }

    fun selectPic(view: View)
    {
        startActivity(Intent(MainActivity@this,JigsawActivity::class.java))
    }
}
