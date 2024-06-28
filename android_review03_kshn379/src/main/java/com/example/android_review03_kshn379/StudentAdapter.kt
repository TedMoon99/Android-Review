package com.example.android_review03_kshn379

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.android_review03_kshn379.databinding.ItemStudentsBinding

class StudentAdapter(val studentInfoList: MutableList<Student>) :
    RecyclerView.Adapter<StudentAdapter.CustomViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentAdapter.CustomViewHolder {
        val binding = ItemStudentsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CustomViewHolder(binding)

    }


    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val student = studentInfoList[position]
        holder.binding.nameRecyclerView.text = student.name
        holder.binding.gradeRecyclerView.text = student.grade.toString()

        holder.itemView.setOnClickListener{
            val total = ThirdFragment()
            val bundle = Bundle().apply {
                putString("name", student.name)
                putInt("grade", student.grade)
            }
            total.arguments = bundle
            (it.context as MainActivity).supportFragmentManager
                .beginTransaction()
                .replace(R.id.containerMain, total)
                .addToBackStack("ThirdFragment")
                .commit()
        }
    }

    override fun getItemCount(): Int {
        return studentInfoList.size
    }


    class CustomViewHolder(val binding: ItemStudentsBinding) : RecyclerView.ViewHolder(binding.root) {
    }
    }