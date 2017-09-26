package com.yuan.jigsaw.adapter

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import com.yuan.jigsaw.R
import com.yuan.jigsaw.model.JigsawInfo
import com.yuan.jigsaw.utils.Constants

/**
 * @author yuan
 * @date 2017/9/26
 */
class JigsawAdapter(private val mContext: Context, val data: ArrayList<JigsawInfo>) : RecyclerView.Adapter<JigsawViewHolder>()
{
    private val mInflater: LayoutInflater = LayoutInflater.from(mContext)
    private var emptyIndex: Int = itemCount - 1

    override fun onBindViewHolder(holder: JigsawViewHolder, position: Int)
    {
        val item = data[position]
        holder.rootView.layoutParams = RecyclerView.LayoutParams(item.width, item.width)
        if (item.isEmptyImg) holder.imgView.setImageResource(Color.WHITE)
        else holder.imgView.setImageBitmap(item.imageBitmap)
        holder.imgView.tag = item.no

        holder.imgView.setOnClickListener { v: View? ->
            if (canExchange(position))
            {
                Log.e("position emptyIndex", "${position}  ${emptyIndex}")
                data[position] = data[emptyIndex]
                data[emptyIndex] = item
                notifyItemChanged(position)
                notifyItemChanged(emptyIndex)
                if (isSuccess())
                {
                    data[position].isEmptyImg = false
                    Toast.makeText(mContext, "you win", Toast.LENGTH_SHORT).show()
                }
                emptyIndex = position
            }
        }
    }

    private fun isSuccess(): Boolean
    {
        var position = 0
        data.forEach {
            if (it.no == position)
            {
                position++
            } else
            {
                return false
            }
        }
        return true
    }

    private fun canExchange(position: Int): Boolean
    {
        if (position == emptyIndex) return false
        val changeIndexArr = arrayListOf<Int>()
        val a = position / Constants.JIGSAW_COUNT
        val b = position % Constants.JIGSAW_COUNT
        if (a > 0) changeIndexArr.add((a - 1) * Constants.JIGSAW_COUNT + b)
        if (a < Constants.JIGSAW_COUNT - 1) changeIndexArr.add((a + 1) * Constants.JIGSAW_COUNT + b)
        if (b > 0) changeIndexArr.add(position - 1)
        if (b < Constants.JIGSAW_COUNT - 1) changeIndexArr.add(position + 1)
        return changeIndexArr.contains(emptyIndex)
    }

    fun notifyEmptyIndex()
    {
        emptyIndex = itemCount - 1
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): JigsawViewHolder? = JigsawViewHolder(mInflater.inflate(R.layout.item_jigsaw, parent, false))
    override fun getItemCount(): Int = data.size
}

class JigsawViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView)
{
    var imgView: ImageView = itemView?.findViewById(R.id.imgView) as ImageView
    var rootView: View = itemView?.findViewById(R.id.rootView)!!
}


