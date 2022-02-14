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

class util(activity: Activity) {

   val activity=activity
    private val gson: Gson = Gson()
    fun jSONtoObj(value:String):ClassData{
        return gson.fromJson(value,ClassData::class.java)
    }
    fun ObjToJSON(classData: ClassData):String{
        return gson.toJson(classData)
    }
    fun getAllClassData():List<ClassData>{
        val mPrefs = activity.getSharedPreferences("classData", AppCompatActivity.MODE_PRIVATE)
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
        val mPrefs = activity.getSharedPreferences("classData", AppCompatActivity.MODE_PRIVATE)
        val classDataString=mPrefs.getString(classId,null)
        if (classDataString != null) {
            return jSONtoObj(classDataString)
        }
        return null
    }

    fun insertNewData(classData:ClassData){
        val mPrefs = activity.getSharedPreferences("classData", AppCompatActivity.MODE_PRIVATE)
        var json=ObjToJSON(classData)
        with (mPrefs.edit()) {
            putString(classData.classId,json)
            apply()
        }
    }
    fun deleteData(classId:String,adapter:RecyclerAdapter){
        val mPrefs = activity.getSharedPreferences("classData", AppCompatActivity.MODE_PRIVATE)
        with (mPrefs.edit()) {
            remove(classId)
            apply()
            adapter.notifyDataSetChanged()
        }
    }

}