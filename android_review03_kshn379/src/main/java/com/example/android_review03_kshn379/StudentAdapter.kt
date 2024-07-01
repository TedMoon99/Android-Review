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

        // 리사이클러 뷰 아이템 이름, 학년 출력
        val student = studentInfoList[position]
        holder.binding.nameRecyclerView.text = student.name
        holder.binding.gradeRecyclerView.text = student.grade.toString()

        // 리사이클러 뷰 아이템 클릭 시 LastFragment 화면 및 데이터 출력
        holder.itemView.setOnClickListener{
            val total = LastFragment()
            val bundle = Bundle().apply {
                putParcelable("student", student)
            }
            total.arguments = bundle
            (it.context as MainActivity).supportFragmentManager
                .beginTransaction()
                .replace(R.id.containerMain, total)
                .addToBackStack("LastFragment")
                .commit()
        }
    }


    override fun getItemCount(): Int {
        return studentInfoList.size
    }


    class CustomViewHolder(val binding: ItemStudentsBinding) : RecyclerView.ViewHolder(binding.root) {
    }
    }

