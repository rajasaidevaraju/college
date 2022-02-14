package com.example.college.data

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.college.AddActivity
import com.example.college.MainActivity
import com.example.college.RecyclerAdapter
import com.google.gson.Gson

class util(mPrefs: SharedPreferences) {

   val mPrefs=mPrefs
    private val gson: Gson = Gson()
    fun jSONtoObj(value:String):ClassData{
        return gson.fromJson(value,ClassData::class.java)
    }
    fun ObjToJSON(classData: ClassData):String{
        return gson.toJson(classData)
    }
    fun getAllClassData():List<ClassData>{

        //val classListData= HashMap<String,ClassData>()
        var classListData= ArrayList<ClassData>()
        for((key,value) in  mPrefs.all){
            if(value is String){
                classListData.add(jSONtoObj((value)))
            }
        }
        return classListData.sortedBy{it.day*100+it.hours}

    }

    fun getClassData(classId:String):ClassData?{

        val classDataString=mPrefs.getString(classId,null)
        if (classDataString != null) {
            return jSONtoObj(classDataString)
        }
        return null
    }

    fun insertNewData(classData:ClassData){

        var json=ObjToJSON(classData)
        with (mPrefs.edit()) {
            putString(classData.classId,json)
            apply()
        }
    }
    fun deleteData(classId:String,adapter:RecyclerAdapter){
        with (mPrefs.edit()) {
            remove(classId)
            apply()
            adapter.notifyDataSetChanged()
        }
    }

    fun getLatestData():ClassData{
        val listData=getAllClassData()
        val currentDay=Days.getCurrentDay()
        val currentHour=Days.getCurrentHour()
        val current=currentDay*100+currentHour
        var result= listData[0]
        for(classData in listData){
            val target=classData.day*100+classData.hours
            if(current>target){
                result=classData
                break
            }
        }
        return result
    }
}