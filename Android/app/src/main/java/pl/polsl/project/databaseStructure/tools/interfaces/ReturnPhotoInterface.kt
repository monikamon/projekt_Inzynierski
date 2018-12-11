@file:Suppress("LiftReturnOrAssignment")

package pl.polsl.project.databaseStructure.tools.interfaces

import android.graphics.Bitmap

interface ReturnPhotoInterface {

    fun returnPhoto(pictureBitmap:Bitmap)

    fun scalePhoto(bitmap:Bitmap):Bitmap{
        if(bitmap.height > 960 || bitmap.width > 960) {
            val ratio = Math.abs(bitmap.height.toFloat() / bitmap.width.toFloat())
            return Bitmap.createScaledBitmap(bitmap, 960, (960.toFloat() * ratio).toInt(), false)
        }
        return bitmap
    }
}