package DataStructure

import java.io.Serializable
import java.util.*

class Product: Serializable{

    var guarrantyDate:String
    var name:String
    var prise:Double
    var photoPicture:Int //chyba nie int, ale komu to przeszkadza, bo miżna zmienić :P

    constructor(guarrantyDate:String, name:String, prise:Double, photoPicture:Int){

        this.guarrantyDate=guarrantyDate
        this.name=name
        this.prise=prise
        this.photoPicture=photoPicture
    }

}