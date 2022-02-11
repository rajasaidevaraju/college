package com.example.college

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.college.data.ClassData
import com.example.college.data.Days
import com.google.gson.Gson





class AddActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        var daySpinner=findViewById<Spinner>(R.id.daySpinner);
        val adapter: ArrayAdapter<Days> = ArrayAdapter<Days>(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            Days.getDays()
        )
        daySpinner.setAdapter(adapter)

    }

    private fun inputCheck(className:String, professorName:String, classId:String):Boolean{

        if(TextUtils.isEmpty(className)){
            Toast.makeText(getApplicationContext(),
                "Class Name cannot be Empty!",
                Toast.LENGTH_LONG).show();
            return false
        }
        return true
    }

    public fun addClass(view:View){
        var className= findViewById<EditText>(R.id.classNameInput).text.toString()
        var professorName= findViewById<EditText>(R.id.professorNameInput).text.toString()
        var classId= findViewById<EditText>(R.id.classIdInput).text.toString()
        var day=findViewById<Spinner>(R.id.daySpinner).selectedItem
        var hour=findViewById<TimePicker>(R.id.startTime).hour
        var minute=findViewById<TimePicker>(R.id.startTime).minute
        var validInput=inputCheck(className, professorName, classId)
        var dayVal=0
        if(day is Days){
            dayVal=day.getId()
        }
        if(validInput){
            val gson = Gson()
            var classData=ClassData(classId,professorName,className,dayVal,hour,minute)
            var json=gson.toJson(classData)
            val mPrefs = getSharedPreferences("classData",MODE_PRIVATE)
            with (mPrefs.edit()) {
                putString(classId,json)
                apply()
                val intent = Intent(this@AddActivity, MainActivity::class.java)
                startActivity(intent)
            }

        }
    }
}