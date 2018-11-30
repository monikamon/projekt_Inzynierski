package com.example.monika.inzynierka.DataStructure.tools

import android.arch.persistence.room.TypeConverter
import com.example.monika.inzynierka.DataStructure.ConstrantExpenseTime

class Converter {

    @TypeConverter
    fun ConstrantExpenseTimeToTnt(enumCET: ConstrantExpenseTime) = enumCET.value

    @TypeConverter
    fun intToConstrantExpenseTime(value: Int) = ConstrantExpenseTime.values().first { it.value == value }

}