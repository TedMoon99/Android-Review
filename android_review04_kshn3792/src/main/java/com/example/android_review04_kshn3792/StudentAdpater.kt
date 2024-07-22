package com.example.android_review04_kshn3792

import android.os.Bundle
import android.text.TextUtils.replace
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.replace
import androidx.recyclerview.widget.RecyclerView
import com.example.android_review04_kshn3792.databinding.ItemsBinding

class StudentAdpater(
    private val dataSet: ArrayList<StudentInfo>, val manager: FragmentManager
) : RecyclerView.Adapter<StudentAdpater.DataViewHolder>() {
    // ViewHolder
    class DataViewHolder(val itemsBinding: ItemsBinding) :
        RecyclerView.ViewHolder(itemsBinding.root) {

        // ViewHolder 클릭 시 작동
        fun onClick(manager: FragmentManager, position: Int) {
            val infoFragment = InfoFragment().apply {
                val data = Bundle().apply {
                    putInt("position",position)
                }
                arguments = data
            }
            // 화면 전환 코드 작성
            manager
                .beginTransaction()
                .replace(R.id.containerMain, infoFragment)
                .addToBackStack(FragmentName.INFO_FRAGMENT.str)
                .commit()
        }
    }

    // ViewHolder 최초 생성 시 작동
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        // binding 적용
        val itemsBinding = ItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DataViewHolder(itemsBinding)

    }

    // 몇 개의 Adapter를 만들어 줄 건가
    override fun getItemCount(): Int {
        return dataSet.size
    }

    // ViewHolder 재사용 시 작동
    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.itemsBinding.textViewItemsName.text = "이름 : ${dataSet[position].studentName}"
        holder.itemsBinding.textViewItemsGrade.text = "학년 : ${dataSet[position].studentGrade} 학년"

        // 클릭 리스너 설정
        holder.itemView.setOnClickListener {
            // 화면 전환
            holder.onClick(manager, position)
        }
    }


}

