package com.example.college

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.college.data.ClassData
import com.google.gson.Gson


class MainActivity : AppCompatActivity() {

    private val gson:Gson=Gson()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // getting the recyclerview by its id
        val recyclerview = findViewById<RecyclerView>(R.id.classDataList)

        // this creates a vertical layout Manager
        recyclerview.layoutManager = LinearLayoutManager(this)

        // ArrayList of class ClassData
        val data = getAllClasses()
        Log.d("prefinput",data.toString())
        // This will pass the ArrayList to our Adapter
        val adapter = RecyclerAdapter(data)

        // Setting the Adapter with the recyclerview
        recyclerview.adapter = adapter
    }

    private fun getAllClasses():List<ClassData>{
        val mPrefs = getSharedPreferences("classData",MODE_PRIVATE)
        //val classListData= HashMap<String,ClassData>()
        val classListData= ArrayList<ClassData>()
        for((key,value) in  mPrefs.all){
            if(value is String){
            classListData.add(jSONtoObj((value)))
            }
        }
        return classListData
    }

    private fun jSONtoObj(value:String):ClassData{
        return gson.fromJson(value,ClassData::class.java)

    }

    fun addNewSchedule(view: View){
        val intent = Intent(this, AddActivity::class.java)
        startActivity(intent)
    }
    override fun onBackPressed() {
        moveTaskToBack(true)
    }
}