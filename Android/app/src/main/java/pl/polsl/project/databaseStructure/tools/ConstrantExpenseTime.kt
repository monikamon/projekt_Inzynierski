package pl.polsl.project.databaseStructure.tools

enum class ConstrantExpenseTime(val value:Int){

    //klasa enum, z której wybiera się, co ile płaci się stały wydatek
    EVERYDAY(0),
    EVERY_WEEK(1),
    EVERY_MONTH(2),
    EVERY_TWO_MONTHS(3),
    EVERY_THREE_MONTHS(4),
    EVERY_SIX_MONTHS(5),
    EVERY_YEAR(6);


}