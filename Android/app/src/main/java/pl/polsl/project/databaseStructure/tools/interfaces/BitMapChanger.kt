@file:Suppress("LiftReturnOrAssignment")

package pl.polsl.project.databaseStructure.tools.interfaces

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.ByteArrayOutputStream

interface BitMapChanger{

    var photoByteArray:ByteArray?

    fun setBitmapPhoto(photoBitmap:Bitmap?){
        if(photoBitmap==null)
            return

        val stream = ByteArrayOutputStream()
        photoBitmap.compress(Bitmap.CompressFormat.PNG, 80, stream)
        this.photoByteArray = stream.toByteArray()
    }

    fun getBitmapPhoto():Bitmap?{

        if(photoByteArray==null){

            return null
        }else {
            //przekonwertowac photoByteArray na image
            return BitmapFactory.decodeByteArray(this.photoByteArray, 0, this.photoByteArray!!.size)
        }

    }
}