package DataStructure

import java.io.Serializable

class Category: Serializable{

    var name:String

    val list = ArrayList<Expense>()


    constructor(name:String){

        this.name=name
    }

    fun getExpenseSum():Double{

        var sum:Double=0.0
        for(i in list) {

            sum += i.price
        }

        return sum
    }

}