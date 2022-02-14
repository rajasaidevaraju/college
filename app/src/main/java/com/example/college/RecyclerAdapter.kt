package com.example.college

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.college.data.ClassData
import com.example.college.data.Days
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class RecyclerAdapter(private val mList: List<ClassData>) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    var onItemClick: ((String) -> Unit)? = null
    lateinit var onLongItemClick: ((String) -> Boolean)
    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.class_card, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val ClassData = mList[position]

        // sets the text to the textview from our itemHolder class
        holder.classNameText.text = ClassData.className
        holder.professorNameText.text = ClassData.professorName
        holder.classIdText.text = ClassData.classId
        holder.dayText.text=Days.getDayForID(ClassData.day)
        holder.timeText.text=Days.getTime(ClassData.hours,ClassData.minutes)

    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views
    inner class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val classNameText: TextView = itemView.findViewById(R.id.classNameText)
        val professorNameText: TextView = itemView.findViewById(R.id.professorNameText)
        val classIdText: TextView = itemView.findViewById(R.id.classIdText)
        val dayText: TextView = itemView.findViewById(R.id.dayText)
        val timeText: TextView = itemView.findViewById(R.id.timeText)
        init{
            ItemView.setOnClickListener{
                onItemClick?.invoke(mList[adapterPosition].classId)
            }
            ItemView.setOnLongClickListener {
                onLongItemClick.invoke(mList[adapterPosition].classId)
            }
        }
    }
}