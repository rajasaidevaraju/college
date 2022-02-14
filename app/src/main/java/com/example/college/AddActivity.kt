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
import com.example.college.data.util
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
        checkForUpdate()


    }

    private fun checkForUpdate(){
        var utils: util =util(this.getSharedPreferences("classData", AppCompatActivity.MODE_PRIVATE))
        val extras=intent.extras
        if(extras!=null){
            val classId=extras.getString("classId")
            if (classId != null) {
                val classData=utils.getClassData(classId)
                if(classData!=null){
                    setTitle("Update Class")
                    fillDetails(classData)
                    findViewById<Button>(R.id.addClassButton).setText("Update Class")
                }

            }
        }
    }
    private fun fillDetails(classData: ClassData) {
        findViewById<EditText>(R.id.classNameInput).setText(classData.className)
        findViewById<EditText>(R.id.professorNameInput).setText(classData.professorName)
        findViewById<EditText>(R.id.classIdInput).setText(classData.classId)
        findViewById<Spinner>(R.id.daySpinner).setSelection(classData.day)
        findViewById<TimePicker>(R.id.startTime).hour=classData.hours
        findViewById<TimePicker>(R.id.startTime).minute=classData.minutes
        findViewById<EditText>(R.id.classIdInput).isEnabled=false
    }

    private fun inputCheck(classData: ClassData):Boolean{

        var valid=true

        if(TextUtils.isEmpty(classData.className)){
            findViewById<EditText>(R.id.classNameInput).setError("Class Name cannot be Empty")
            valid=false;
        }
        if(TextUtils.isEmpty(classData.professorName)){
            findViewById<EditText>(R.id.professorNameInput).setError("Professor Name cannot be Empty")
            valid=false;
        }
        if(TextUtils.isEmpty(classData.classId)){
            findViewById<EditText>(R.id.classIdInput).setError("Class ID cannot be Empty")
            valid=false;
        }
        return valid
    }

    private fun getClassData():ClassData{
        var className= findViewById<EditText>(R.id.classNameInput).text.toString()
        var professorName= findViewById<EditText>(R.id.professorNameInput).text.toString()
        var classId= findViewById<EditText>(R.id.classIdInput).text.toString()
        var day=findViewById<Spinner>(R.id.daySpinner).selectedItem
        var hour=findViewById<TimePicker>(R.id.startTime).hour
        var minute=findViewById<TimePicker>(R.id.startTime).minute

        var dayVal=0
        if(day is Days){
            dayVal=day.getId()
        }

        var classData=ClassData(classId,professorName,className,dayVal,hour,minute)
        return classData
    }

    fun addClass(view:View){
        var utils: util =util(this.getSharedPreferences("classData", AppCompatActivity.MODE_PRIVATE))
        var classData=getClassData()
        var validInput=inputCheck(classData)
        if(validInput){
            utils.insertNewData(classData)
            val intent = Intent(this@AddActivity, MainActivity::class.java)
            startActivity(intent)
        }
    }

}