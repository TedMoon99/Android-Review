package com.example.android_review02_kshn379

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ResearchAdapter(val searchList: MutableList<Research>) :
    RecyclerView.Adapter<ResearchAdapter.CustomViewHolder>() {

    class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name = itemView.findViewById<TextView>(R.id.rv_name)
    }

    // 항목 최초 생성시 작동
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ResearchAdapter.CustomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return CustomViewHolder(view)
    }


    // 재호출시 작동
    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.name.text = searchList[position].name
    }

    // 얼마나 만들 것인지
    override fun getItemCount(): Int {
        return searchList.size
    }
}