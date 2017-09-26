package com.yuan.jigsaw.utils

import android.graphics.Bitmap
import com.yuan.jigsaw.model.JigsawInfo
import android.graphics.BitmapFactory
import android.content.ContentResolver
import android.net.Uri
import java.io.FileNotFoundException
import java.io.IOException
import java.util.*


/**
 * @author yuan
 * @date 2017/9/26
 */
object ImageUtils
{
    fun split(bitmap: Bitmap, xPiece: Int, yPiece: Int): ArrayList<JigsawInfo>
    {
        val pieces = ArrayList<JigsawInfo>(xPiece * yPiece)
        val width = bitmap.width
        val height = bitmap.height
        val pieceWidth = width / xPiece
        val pieceHeight = height / yPiece
        for (i in 0 until yPiece)
        {
            for (j in 0 until xPiece)
            {
                val piece = JigsawInfo()
                piece.no = j + i * xPiece;
                val xValue = j * pieceWidth
                val yValue = i * pieceHeight
                piece.height = pieceHeight
                piece.width = pieceWidth
                piece.imageBitmap = Bitmap.createBitmap(bitmap, xValue, yValue, pieceWidth, pieceHeight)
                pieces.add(piece)
            }
        }
        return pieces
    }

    @Throws(FileNotFoundException::class, IOException::class)
    fun getBitmap(cr: ContentResolver, url: Uri): Bitmap
    {
        val input = cr.openInputStream(url)
        val bitmap = BitmapFactory.decodeStream(input)
        input!!.close()
        return bitmap
    }

    /**
     * 随机生成拼图顺序
     * */
    fun genRandomJigsaw(arr: ArrayList<JigsawInfo>)
    {
        val info = arr.last()
        info.isEmptyImg = true
        arr.removeAt(arr.lastIndex)
        Collections.shuffle(arr)
        arr.add(info)
    }
}