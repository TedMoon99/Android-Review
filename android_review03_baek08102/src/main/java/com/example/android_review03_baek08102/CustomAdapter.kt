package com.example.android_review03_baek08102

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CustomAdapter(
    private val studentData: List<StudentData>,
) : RecyclerView.Adapter<CustomAdapter.CustomViewHolder>() {

    class CustomViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView
        val grade: TextView

        init {
            name = view.findViewById(R.id.row_textView_name)
            grade = view.findViewById(R.id.row_textView_grade)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_main, parent, false)

        return CustomViewHolder(view)
    }

    override fun getItemCount(): Int {
        return studentData.size
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.name.text = studentData[position].name
        holder.grade.text = studentData[position].grade.toString()
    }
}