package com.example.android_review02_baek08102

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// 어댑터 구현
class MyAdapter(private val nameData: List<String>) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() { //에러

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView

        init {
            name = view.findViewById(R.id.textView_row_main) //에러
        }
    }

    // ViewHolder 생성 시 호출
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_main, parent, false)

        return MyViewHolder(view) //에러
    }

    // 출력할 항목 개수 반환
    override fun getItemCount(): Int {
        return nameData.size
    }

    // ViewHolder에 Name data class의 데이터 바인딩, 재사용시 호출
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.name.text = nameData[position]
    }
}