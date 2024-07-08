package com.example.android_review04_tedmoon.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android_review04_tedmoon.R
import com.example.android_review04_tedmoon.databinding.RowBinding
import com.example.android_review04_tedmoon.fragment.ShowFragment
import com.example.android_review04_tedmoon.model.ScoreInfo
import com.example.android_review04_tedmoon.utils.FragmentName

class CustomAdapter(private val dataSet: ArrayList<ScoreInfo>, val manager: FragmentManager): RecyclerView.Adapter<CustomAdapter.ViewHolder>() {
    class ViewHolder(val rowBinding: RowBinding): RecyclerView.ViewHolder(rowBinding.root){
        // ViewHolder 클릭 시 작동
        fun clickListenr(manager: FragmentManager){
            manager
                .beginTransaction() // 트랜잭션 생성
                .replace(R.id.containerMain, ShowFragment()) // ShowFragment로 변경
                .addToBackStack(FragmentName.SHOW_FRAGMENT.str) // 백스택에 추가
                .commit() // 실행
        }
    }

    // ViewHolder 최초 생성 시 작동
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // binding 적용
        val rowBinding = RowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(rowBinding)
    }

    // 몇 개의 Adapter를 만들어 줄 것인가
    override fun getItemCount(): Int {
        return dataSet.size
    }

    // ViewHolder 재사용시 작동
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.rowBinding.textViewRowName.text = "이름 : ${dataSet[position].name}"
        holder.rowBinding.textViewRowGrade.text = "학년 : ${dataSet[position].grade}학년"
        // 항목 클릭시 리스너 설정
        holder.itemView.setOnClickListener {
            holder.clickListenr(manager)
        }

    }
}