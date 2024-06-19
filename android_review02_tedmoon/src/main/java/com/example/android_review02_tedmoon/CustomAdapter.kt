package com.example.android_review02_tedmoon

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CustomAdapter(val dataList: List<String>): RecyclerView.Adapter<CustomAdapter.CustomViewHolder>() {
    class CustomViewHolder(view: View): RecyclerView.ViewHolder(view){
        val name: TextView

        init {
            name = view.findViewById(R.id.textView_row_name)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        // rowMain 화면을 만들어서
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_main, parent, false)
        // Viewholder에 담아 반환한다
        return CustomViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.name.text = dataList[position]
    }
}