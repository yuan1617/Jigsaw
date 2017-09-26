@file:Suppress("UNCHECKED_CAST")

package com.yuan.jigsaw.views

import android.graphics.Color
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import com.yuan.jigsaw.R
import com.yuan.jigsaw.adapter.JigsawAdapter
import com.yuan.jigsaw.model.JigsawInfo
import com.yuan.jigsaw.utils.Constants
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
    private lateinit var refreshArr: ArrayList<JigsawInfo>
    lateinit var adapter : JigsawAdapter

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
        picArr = ImageUtils.split(picBitmap, Constants.JIGSAW_COUNT, Constants.JIGSAW_COUNT)
        ImageUtils.genRandomJigsaw(picArr)
        refreshArr = picArr.clone() as ArrayList<JigsawInfo>

        val layoutParams = LinearLayout.LayoutParams(screenWidth / Constants.JIGSAW_COUNT, screenWidth / Constants.JIGSAW_COUNT)
        layoutParams.gravity = Gravity.CENTER_HORIZONTAL
        layoutParams.setMargins(10, 10, 10, 10)
        jigsawImageView.layoutParams = layoutParams
        jigsawImageView.setImageBitmap(picBitmap)

        jigsawRecyclerView.layoutManager = GridLayoutManager(applicationContext, 3)
        jigsawRecyclerView.addItemDecoration(GridDividerItemDecoration(1, Color.WHITE))
        jigsawRecyclerView.itemAnimator = DefaultItemAnimator()
        adapter = JigsawAdapter(applicationContext, picArr)
        jigsawRecyclerView.adapter = adapter
    }


    fun refreshClick(view : View)
    {
        picArr.clear()
        picArr.addAll(refreshArr)
        adapter.notifyDataSetChanged()
        adapter.notifyEmptyIndex()
    }
}
