package DataStructure

import java.io.Serializable

class Category: Serializable{

    var name:String
    var receipt:Int //chyba nie, potem zmienię XD
    var receiptSum:Double

    constructor(name:String, receipt:Int, receiptSum:Double){

        this.name=name
        this.receipt=receipt
        this.receiptSum=receiptSum
    }

    val list = arrayListOf<Receipt>()
}