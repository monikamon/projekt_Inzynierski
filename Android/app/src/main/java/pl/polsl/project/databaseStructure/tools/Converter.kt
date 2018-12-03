package pl.polsl.project.databaseStructure.tools

import android.arch.persistence.room.TypeConverter

class Converter {

    @TypeConverter
    fun ConstrantExpenseTimeToTnt(enumCET: ConstrantExpenseTime) = enumCET.value

    @TypeConverter
    fun intToConstrantExpenseTime(value: Int) = ConstrantExpenseTime.values().first { it.value == value }

}