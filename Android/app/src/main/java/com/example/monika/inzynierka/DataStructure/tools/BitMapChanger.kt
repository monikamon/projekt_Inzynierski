package com.example.monika.inzynierka.DataStructure.tools

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.ByteArrayOutputStream

interface BitMapChanger{

    var photoByteArray:ByteArray?
    var photo: Bitmap?

    fun setBitmapPhoto(photoBitmap:Bitmap?){
        if(photoBitmap==null)
            return

        this.photo = photoBitmap

        val stream = ByteArrayOutputStream()
        photoBitmap.compress(Bitmap.CompressFormat.PNG, 80, stream)
        this.photoByteArray = stream.toByteArray()
    }

    fun getBitmapPhoto():Bitmap?{

        if(photo==null && photoByteArray==null){

            return null
        }else if(photo==null){
            //przekonwertowac photoByteArray na image
            photo = BitmapFactory.decodeByteArray(this.photoByteArray, 0, this.photoByteArray!!.size)
        }
            return photo

    }
}