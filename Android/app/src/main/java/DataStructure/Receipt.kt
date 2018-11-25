package DataStructure

import android.printservice.PrintService
import java.io.Serializable

class Receipt: Serializable{

    var shoppingDate:String
    var receiptPhoto:Int //zmienię, obiecuję :P
    var prise:Double
    var constrantExpensive:Boolean

    constructor(shoppingDate:String, receiptPhoto:Int, prise:Double, constrantExpensive:Boolean){

        this.shoppingDate=shoppingDate
        this.receiptPhoto=receiptPhoto
        this.prise=prise
        this.constrantExpensive=constrantExpensive
    }

    val list = arrayListOf<Product>()

}