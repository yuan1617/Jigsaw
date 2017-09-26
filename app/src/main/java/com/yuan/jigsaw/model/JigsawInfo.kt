package com.yuan.jigsaw.model

import android.databinding.BaseObservable
import android.databinding.Bindable
import android.graphics.Bitmap

class JigsawInfo : BaseObservable()
{
    @Bindable
    var no : Int = 0

    @Bindable
    var imageBitmap :Bitmap? = null
}