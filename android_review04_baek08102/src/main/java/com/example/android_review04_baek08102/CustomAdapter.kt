package com.example.android_review04_baek08102

import android.os.Bundle
import android.text.TextUtils.replace
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android_review04_baek08102.databinding.RowMainBinding

class CustomAdapter(
    private val studentData: List<StudentData>,
    private val fragmentManager: FragmentManager
) :
    RecyclerView.Adapter<CustomAdapter.CustomViewHolder>() {
    class CustomViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView
        val grade: TextView

        fun clickListener(studentData: StudentData, fragmentManager: FragmentManager) {

            val bundle = Bundle().apply {
                putParcelable("studentInform", studentData)
            }

            SubFragment3().arguments = bundle

            fragmentManager.beginTransaction()
                .replace(R.id.main_container, SubFragment3())
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

        holder.clickListener(studentData[position], fragmentManager)
    }
}