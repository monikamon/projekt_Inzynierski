package pl.polsl.project.databaseStructure.tools

import android.arch.persistence.room.TypeConverter

class Converter {

    //zamiana wartości enum na liczbę i odwrotnie
    @TypeConverter
    fun constrantExpenseTimeToTnt(enumCET: ConstrantExpenseTime) = enumCET.value

    @TypeConverter
    fun intToConstrantExpenseTime(value: Int) = ConstrantExpenseTime.values().first { it.value == value }

}