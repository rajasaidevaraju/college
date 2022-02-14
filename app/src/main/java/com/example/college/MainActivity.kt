package com.example.college


import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.college.data.util
import com.google.gson.Gson


class MainActivity : AppCompatActivity() {

    private val gson:Gson=Gson()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpView()
    }

    private fun setUpView(){
        var utils: util =util(this.getSharedPreferences("classData", AppCompatActivity.MODE_PRIVATE))
        // getting the recyclerview by its id
        val recyclerview = findViewById<RecyclerView>(R.id.classDataList)

        // this creates a vertical layout Manager
        recyclerview.layoutManager = LinearLayoutManager(this)

        // ArrayList of class ClassData
        val data = utils.getAllClassData()
        // This will pass the ArrayList to our Adapter
        val adapter = RecyclerAdapter(data)

        // Setting the Adapter with the recyclerview
        recyclerview.adapter = adapter
        adapter.onItemClick= { classId ->
            val intent = Intent(this, AddActivity::class.java)
            intent.putExtra("classId",classId)
            startActivity(intent)
        }

        adapter.onLongItemClick= { classId ->
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Delete Class?")
            builder.setPositiveButton("Yes"){dialogInterface, which ->
                utils.deleteData(classId,adapter)
                Toast.makeText(applicationContext,"Class Deleted",Toast.LENGTH_LONG).show()
                setUpView()
            }
            builder.setNeutralButton("Cancel"){dialogInterface , which ->
                Toast.makeText(applicationContext,"Clicked Cancel",Toast.LENGTH_LONG).show()
            }
            val alertDialog: AlertDialog = builder.create()
            alertDialog.setCancelable(false)
            alertDialog.show()
            true
        }
    }

    fun addNewSchedule(view: View){
        val intent = Intent(this, AddActivity::class.java)
        startActivity(intent)
    }
    override fun onBackPressed() {
        moveTaskToBack(true)
    }
}