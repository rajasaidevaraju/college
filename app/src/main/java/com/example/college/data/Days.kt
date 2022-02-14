package com.example.college.data

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*
import kotlin.collections.ArrayList

class Days constructor(id: Int,name: String){
    private var id: Int = 0
    private var name: String = "Monday"


    init {
        setId(id)
        setName(name)
    }


    fun getId(): Int {
        return id
    }

    private fun setId(id: Int) {
        this.id = id
    }

    private fun getName(): String {
        return name
    }

    private fun setName(name: String) {
        this.name = name
    }


    //to display object as a string in spinner
    override fun toString(): String {
        return name
    }

    override fun equals(other: Any?): Boolean {
        if (other is Days) {
            val c: Days = other
            if (c.getName() == name && c.getId() == id) return true
        }
        return false
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + name.hashCode()
        return result
    }

    companion object{
        fun getDays():MutableList<Days>{
            val daysList: MutableList<Days> = ArrayList()
            daysList.add(Days(0,"Monday"))
            daysList.add(Days(1,"Tuesday"))
            daysList.add(Days(2,"Wednesday"))
            daysList.add(Days(3,"Thursday"))
            daysList.add(Days(4,"Friday"))
            daysList.add(Days(5,"Saturday"))
            daysList.add(Days(6,"Sunday"))

            return daysList
        }
        fun getDayForID(id:Int):String{
            var returnVal="Monday"
            when(id){
                0->returnVal="Monday"
                1->returnVal="Tuesday"
                2->returnVal="Wednesday"
                3->returnVal="Thursday"
                4->returnVal="Friday"
                5->returnVal="Saturday"
                6->returnVal="Sunday"
            }
            return returnVal
        }

        fun getCurrentDay():Int{
            return LocalDate.now().dayOfWeek.value-1
        }
        fun getCurrentHour():Int{
            return Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        }
        fun getTime(hours:Int,minutes:Int):String{
            val s = "${hours}:${minutes}"
            val f1: DateFormat = SimpleDateFormat("HH:mm")
            val d: Date = f1.parse(s)
            val f2: DateFormat = SimpleDateFormat("h:mm a")
            return f2.format(d).uppercase()
        }
    }
}
