package com.yuan.jigsaw.utils

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.ColorInt;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;


@Suppress("NAME_SHADOWING") class GridDividerItemDecoration(private val mDividerWidth: Int, @ColorInt color: Int) : RecyclerView.ItemDecoration()
{
    private val mPaint: Paint?

    init
    {
        mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mPaint.color = color
        mPaint.style = Paint.Style.FILL
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State)
    {
        super.getItemOffsets(outRect, view, parent, state)
        val itemPosition = (view.getLayoutParams() as RecyclerView.LayoutParams).viewLayoutPosition
        val spanCount = getSpanCount(parent)
        val childCount = parent.adapter.itemCount

        val isLastRow = isLastRow(parent, itemPosition, spanCount, childCount)
        val isFirstRow = isfirstRow(parent, itemPosition, spanCount, childCount)

        val top: Int
        val left: Int
        val right: Int
        val bottom: Int
        val eachWidth = (spanCount - 1) * mDividerWidth / spanCount
        val dl = mDividerWidth - eachWidth

        left = itemPosition % spanCount * dl
        right = eachWidth - left
        bottom = mDividerWidth
        if (isFirstRow)
        {
            top = (spanCount - 1) * mDividerWidth / spanCount
        } else
        {
            top = 0
        }
        outRect.set(left, top, right, bottom)
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State)
    {
        super.onDraw(c, parent, state)
        draw(c, parent)
    }

    private fun draw(canvas: Canvas, parent: RecyclerView)
    {
        val childSize = parent.childCount
        for (i in 0 until childSize)
        {
            val child = parent.getChildAt(i)
            val layoutParams = child.layoutParams as RecyclerView.LayoutParams

            var left = child.left
            var right = child.right
            var top = child.bottom + layoutParams.bottomMargin
            var bottom = top + mDividerWidth
            if (mPaint != null)
            {
                canvas.drawRect(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(), mPaint)
            }
            top = child.top
            bottom = child.bottom + mDividerWidth
            left = child.right + layoutParams.rightMargin
            right = left + mDividerWidth
            if (mPaint != null)
            {
                canvas.drawRect(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(), mPaint)
            }
        }
    }

    private fun isLastColumn(parent: RecyclerView, pos: Int, spanCount: Int, childCount: Int): Boolean
    {
        var childCount = childCount
        val layoutManager = parent.layoutManager
        if (layoutManager is GridLayoutManager)
        {
            if ((pos + 1) % spanCount == 0)
            {
                return true
            }
        } else if (layoutManager is StaggeredGridLayoutManager)
        {
            val orientation = layoutManager.orientation
            if (orientation == StaggeredGridLayoutManager.VERTICAL)
            {
                if ((pos + 1) % spanCount == 0)
                {
                    return true
                }
            } else
            {
                childCount -= childCount % spanCount
                if (pos >= childCount)
                    return true
            }
        }
        return false
    }

    private fun isLastRow(parent: RecyclerView, pos: Int, spanCount: Int, childCount: Int): Boolean
    {
        var childCount = childCount
        val layoutManager = parent.layoutManager
        if (layoutManager is GridLayoutManager)
        {
            val lines = if (childCount % spanCount == 0) childCount / spanCount else childCount / spanCount + 1
            return lines == pos / spanCount + 1
        } else if (layoutManager is StaggeredGridLayoutManager)
        {
            val orientation = layoutManager.orientation
            if (orientation == StaggeredGridLayoutManager.VERTICAL)
            {
                childCount -= childCount % spanCount
                if (pos >= childCount) return true
            } else
            {
                if ((pos + 1) % spanCount == 0)
                {
                    return true
                }
            }
        }
        return false
    }

    private fun isfirstRow(parent: RecyclerView, pos: Int, spanCount: Int, childCount: Int): Boolean
    {
        var childCount = childCount
        val layoutManager = parent.layoutManager
        if (layoutManager is GridLayoutManager)
        {
            val lines = if (childCount % spanCount == 0) childCount / spanCount else childCount / spanCount + 1
            return pos / spanCount + 1 == 1
        } else if (layoutManager is StaggeredGridLayoutManager)
        {
            val orientation = layoutManager.orientation
            if (orientation == StaggeredGridLayoutManager.VERTICAL)
            {
                childCount = childCount - childCount % spanCount
                if (pos >= childCount) return true
            } else
            {
                if ((pos + 1) % spanCount == 0)
                {
                    return true
                }
            }
        }
        return false
    }

    private fun getSpanCount(parent: RecyclerView): Int
    {
        var spanCount = -1
        val layoutManager = parent.layoutManager
        if (layoutManager is GridLayoutManager)
        {
            spanCount = layoutManager.spanCount
        } else if (layoutManager is StaggeredGridLayoutManager)
        {
            spanCount = layoutManager.spanCount
        }
        return spanCount
    }
}