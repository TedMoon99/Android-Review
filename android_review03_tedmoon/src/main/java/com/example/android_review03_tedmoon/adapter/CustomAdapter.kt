package com.example.android_review03_tedmoon.adapter

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android_review03_tedmoon.R
import com.example.android_review03_tedmoon.databinding.RowMainBinding
import com.example.android_review03_tedmoon.fragment.SubFragment3
import com.example.android_review03_tedmoon.model.ScoreInfo
import com.example.android_review03_tedmoon.utils.FragmentName

class CustomAdapter(private val dataSet: ArrayList<ScoreInfo>, private val manager: FragmentManager): RecyclerView.Adapter<CustomAdapter.CustomViewHolder>() {
    class CustomViewHolder(val rowMainBinding: RowMainBinding): RecyclerView.ViewHolder(rowMainBinding.root){

        // 클릭 리스너 설정
        fun clickListener(scoreInfo: ScoreInfo, manager: FragmentManager){
            // Fragment 생성
            val subFragment3 = SubFragment3().apply {
                arguments = Bundle().apply {
                    putParcelable("positionData", scoreInfo)
                }
            }
            // 화면 이동
            manager
                .beginTransaction() // 트랜잭션 생성
                .replace(R.id.containerMain, subFragment3) // 화면 이동
                .addToBackStack(FragmentName.SUB_FRAGMENT3.name) // 백스택 추가
                .commit() // 실행
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

        holder.itemView.setOnClickListener {
            val data = dataSet[position]
            Log.d("test1234", "데이터 전송 확인 : $data")
            // 클릭 리스너
            holder.clickListener(data, manager)
        }
    }
}