package com.example.android_review04_baek08102

import android.os.Bundle
import android.text.TextUtils.replace
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android_review04_baek08102.databinding.RowMainBinding

class CustomAdapter(
    private val studentData: ArrayList<StudentData>,
    private val fragmentManager: FragmentManager
) : RecyclerView.Adapter<CustomAdapter.CustomViewHolder>() {

    class CustomViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView
        val grade: TextView

        // 리사이클러뷰 아이템 클릭 리스너 설정 함수
        fun clickListener(
            studentData: StudentData,
            fragmentManager: FragmentManager,
        ) {

            val bundle = Bundle().apply {
                putParcelable("studentInform", studentData)
            }
            val subFragment3 = SubFragment3()
            subFragment3.arguments = bundle

            fragmentManager.beginTransaction()
                .replace(R.id.main_container, subFragment3)
                .addToBackStack(FragmentName.SUB_FRAGMENT3.name)
                .commit()
        }

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

        Log.d("test1", "show holder data -> name : ${holder.name.text}, grade : ${holder.grade.text}")

        // 리사이클러뷰 아이템 항목 클릭 시
        holder.itemView.setOnClickListener {
            // SubFragment3에게 position번째 데이터 전달
            holder.clickListener(studentData[position], fragmentManager)
        }
    }
}