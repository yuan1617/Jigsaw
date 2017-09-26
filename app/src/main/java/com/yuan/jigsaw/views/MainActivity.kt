package com.yuan.jigsaw.views

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.yuan.jigsaw.R
import android.graphics.Bitmap
import android.net.Uri
import java.io.File
import com.yalantis.ucrop.UCrop
import java.util.*

/**
 * @author yuan
 * @date 2017/9/26
 * */
class MainActivity : BaseActivity()
{
    val REQUEST_CODE_PICK_IMAGE = 1001

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
                    Toast.makeText(applicationContext, "authorize success", Toast.LENGTH_SHORT).show()
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
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"//相片类型
        startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
    {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK)
        {
            when (requestCode)
            {
                REQUEST_CODE_PICK_IMAGE ->
                {
                    try
                    {
                        val uri = data?.getData()
                        if (uri != null)
                        {
                            val uCrop = UCrop.of(uri, Uri.fromFile(File(cacheDir, UUID.randomUUID().toString())))
                            advancedConfig(uCrop)
                            uCrop.withAspectRatio(screenWidth.toFloat(),screenWidth.toFloat())
                            uCrop.withMaxResultSize(screenWidth, screenWidth);
                            uCrop.start(this@MainActivity)
                        }
                    } catch (e: Exception)
                    {
                        e.printStackTrace()
                        Log.e(MainActivity::class.java.simpleName, e.message)
                    }

                }
                UCrop.REQUEST_CROP ->
                {
                    if (data != null)
                    {
                        val resultUri = UCrop.getOutput(data);
                        if (resultUri != null)
                        {
                            val intent = Intent(this@MainActivity, JigsawActivity::class.java)
                            intent.data = resultUri
                            startActivity(intent)
                        } else
                        {
                            Log.e(MainActivity::class.java.simpleName, "crop error")
                        }
                    }
                }
                else -> Log.e(MainActivity::class.java.simpleName, "unknown request")
            }
        }
    }

    private fun advancedConfig(uCrop: UCrop)
    {
        val options = UCrop.Options()
        options.setCompressionFormat(Bitmap.CompressFormat.JPEG)
        uCrop.withOptions(options)
    }

}

