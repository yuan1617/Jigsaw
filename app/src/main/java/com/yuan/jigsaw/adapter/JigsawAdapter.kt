package com.yuan.jigsaw.adapter

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.yuan.jigsaw.R
import com.yuan.jigsaw.model.JigsawInfo

/**
 * @author yuan
 * @date 2017/9/26
 */
class JigsawAdapter(val mContext: Context, val data: ArrayList<JigsawInfo>) : RecyclerView.Adapter<JigsawViewHolder>()
{
    var mInflater: LayoutInflater = LayoutInflater.from(mContext)

    override fun onBindViewHolder(holder: JigsawViewHolder, position: Int)
    {
        val item = data[position]
        holder.rootView.layoutParams = RecyclerView.LayoutParams(item.width, item.width)
        if(item.no == data.size - 1)
            holder.imgView.setBackgroundColor(Color.WHITE)
        else
            holder.imgView.setImageBitmap(item.imageBitmap)
        holder.imgView.tag = item.no
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): JigsawViewHolder? = JigsawViewHolder(mInflater.inflate(R.layout.item_jigsaw, parent, false))
    override fun getItemCount(): Int = data.size
}

class JigsawViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView)
{
    var imgView: ImageView = itemView?.findViewById(R.id.imgView) as ImageView
    var rootView: View = itemView?.findViewById(R.id.rootView)!!
}

interface JigsawListener
{
    fun onClick(item:JigsawInfo)


}

