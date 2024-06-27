package com.example.android_review03_tedmoon.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.android_review03_tedmoon.databinding.RowMainBinding
import com.example.android_review03_tedmoon.model.ScoreInfo

class CustomAdapter(val dataSet: ArrayList<ScoreInfo>): RecyclerView.Adapter<CustomAdapter.CustomViewHolder>() {
    class CustomViewHolder(rowMainBinding: RowMainBinding): RecyclerView.ViewHolder(rowMainBinding.root){
        val rowMainBinding: RowMainBinding
        init {
            this.rowMainBinding = rowMainBinding
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val rowMainBinding = RowMainBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val viewHolder = CustomViewHolder(rowMainBinding)
        return viewHolder
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.rowMainBinding.textViewRowName.text = "이름 : ${dataSet[position].name}"
        holder.rowMainBinding.textViewRowGrade.text = "학년 : ${dataSet[position].grade}학년"
    }
}