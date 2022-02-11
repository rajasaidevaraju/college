package com.example.college.data

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

    fun setId(id: Int) {
        this.id = id
    }

    fun getName(): String {
        return name
    }

    fun setName(name: String) {
        this.name = name
    }


    //to display object as a string in spinner
    override fun toString(): String {
        return name
    }

    override fun equals(obj: Any?): Boolean {
        if (obj is Days) {
            val c: Days = obj as Days
            if (c.getName() == name && c.getId() === id) return true
        }
        return false
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

            return daysList;
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
    }
}

class DaysList{

}