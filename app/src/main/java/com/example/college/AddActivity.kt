package com.example.college

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner

class AddActivity : AppCompatActivity() {

    lateinit var spinner: Spinner
    lateinit var day : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        spinner =findViewById(R.id.daySpinner)
        // Create an ArrayAdapter using the string array and a default spinner layout
        var adapter= ArrayAdapter.createFromResource(
            this,
            R.array.daysArray,
            android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.adapter=adapter;

    spinner.onItemSelectedListener= object:AdapterView.OnItemSelectedListener{
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            day= adapter.getItem(position).toString()
            Log.d("spinner",day);
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {
            TODO("Not yet implemented")
        }

    }


    }
}