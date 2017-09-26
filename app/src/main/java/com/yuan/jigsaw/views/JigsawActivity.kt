package com.yuan.jigsaw.views

import android.graphics.Color
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.widget.ImageView
import android.widget.LinearLayout
import com.yuan.jigsaw.R
import com.yuan.jigsaw.adapter.JigsawAdapter
import com.yuan.jigsaw.model.JigsawInfo
import com.yuan.jigsaw.utils.GridDividerItemDecoration
import com.yuan.jigsaw.utils.ImageUtils

/**
 * @author yuan
 * @date 2017/9/6
 */
class JigsawActivity : BaseActivity()
{
    private lateinit var picArr: ArrayList<JigsawInfo>
    private lateinit var jigsawImageView: ImageView
    private lateinit var jigsawRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jigsaw)
        init()
    }

    private fun init()
    {
        jigsawImageView = findViewById(R.id.jigsawImageView) as ImageView
        jigsawRecyclerView = findViewById(R.id.jigsawRecyclerView) as RecyclerView

        val picBitmap = ImageUtils.getBitmap(contentResolver, intent.data)
        picArr = ImageUtils.split(picBitmap, 3, 3)
        ImageUtils.genRandomJigsaw(picArr)

        val layoutParams = LinearLayout.LayoutParams(screenWidth/3,screenWidth/3)
        layoutParams.gravity = Gravity.CENTER_HORIZONTAL
        layoutParams.setMargins(10,10,10,10)
        jigsawImageView.layoutParams = layoutParams
        jigsawImageView.setImageBitmap(picBitmap)

        jigsawRecyclerView.layoutManager = GridLayoutManager(applicationContext,3)
        jigsawRecyclerView.addItemDecoration(GridDividerItemDecoration(1, Color.WHITE))
        jigsawRecyclerView.adapter = JigsawAdapter(applicationContext,picArr)
    }
}
