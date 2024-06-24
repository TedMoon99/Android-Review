package com.example.android_review03_kshn379

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.android_review03_kshn379.databinding.ActivityMainBinding
import com.example.android_review03_kshn379.databinding.ListItemsBinding

class StudentAdapter(private val studentList: MutableList<Student>) :
    RecyclerView.Adapter<StudentAdapter.CustomViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val binding = ListItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val viewHolder = CustomViewHolder(binding)

        return CustomViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val student = studentList[position]
        holder.binding.rvMainName.text = student.name
        holder.binding.rvMainGrade.text = student.grade
        holder.itemView.setOnClickListener {
            val studentMainItem =
                holder.itemView.findViewById<View>(R.id.student_main_item)
            studentMainItem.visibility = View.VISIBLE
        }
        holder.binding.returnMainFirst.setOnClickListener {
            val returnMainFirst =
                holder.itemView.findViewById<View>(R.id.student_main_item)
            returnMainFirst.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int = studentList.size

    class CustomViewHolder(val binding: ListItemsBinding) : RecyclerView.ViewHolder(binding.root)
}

