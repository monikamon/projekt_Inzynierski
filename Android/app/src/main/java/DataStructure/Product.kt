package DataStructure

import android.graphics.Bitmap
import java.io.Serializable
import java.util.*

class Product: Serializable{

    var guarrantyDate:String?
    var name:String
    var prise:Double
    var photoPicture:Bitmap?

    constructor(){

        this.guarrantyDate=null
        this.name=""
        this.prise=0.0
        this.photoPicture=null
    }
    constructor(name:String, prise:Double, guarrantyDate:String?=null, photoPicture:Bitmap?=null){

        this.guarrantyDate=guarrantyDate
        this.name=name
        this.prise=prise
        this.photoPicture=photoPicture
    }

}