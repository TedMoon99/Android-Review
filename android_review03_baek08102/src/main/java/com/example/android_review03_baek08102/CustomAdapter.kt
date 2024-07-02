package com.example.android_review03_baek08102

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android_review03_baek08102.databinding.ActivityMainBinding

class CustomAdapter(
    private val studentData: List<StudentData>,
    private val viewModel: CustomViewModel,
    private val mainActivity: MainActivity
) : RecyclerView.Adapter<CustomAdapter.CustomViewHolder>() {
    class CustomViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView
        val grade: TextView


        // 버튼 클릭 시 동작 함수
        fun clickListener(mainActivity: MainActivity) {

            mainActivity.switchFragment(FragmentName.INFORM_FRAGMENT) // 프래그먼트 전환
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
        holder.grade.text = "${studentData[position].grade} 학년"
        // 버튼 클릭 시
        holder.itemView.setOnClickListener {
            // clickListener 함수 실행
            holder.clickListener(mainActivity)
            viewModel.getPosition(position)
        }
    }
}