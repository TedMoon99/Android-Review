package com.example.android_review05_baek08102.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android_review05_baek08102.Fragment.ShowFragment
import com.example.android_review05_baek08102.Model.StudentData
import com.example.android_review05_baek08102.R

class CustomAdapter(
    private val studentData: ArrayList<StudentData>,
    private val fragmentManager: FragmentManager
) : RecyclerView.Adapter<CustomAdapter.CustomViewHolder>() {
    class CustomViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val name: TextView
        val grade: TextView

        init {
            name = view.findViewById(R.id.main_row_name)
            grade = view.findViewById(R.id.main_row_grade)
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

    }

}