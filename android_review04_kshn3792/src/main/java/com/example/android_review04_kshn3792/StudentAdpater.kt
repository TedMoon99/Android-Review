package com.example.android_review04_kshn3792

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android_review04_kshn3792.databinding.ItemsBinding

class StudentAdpater (
    private val dataSet : ArrayList<StudentInfo>, val manager: FragmentManager): RecyclerView.Adapter<StudentAdpater.DataViewHolder>() {
        // ViewHolder
    class DataViewHolder (itemsBinding: ItemsBinding, manager: FragmentManager):RecyclerView.ViewHolder(itemsBinding.root){
        val itemsBinding: ItemsBinding

        init {
            this.itemsBinding = itemsBinding

            // 항목의 클릭 영역을 항목 자체로 설정한다
            itemsBinding.root.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, // 가로 전체 너비 클릭
                ViewGroup.LayoutParams.WRAP_CONTENT // 세로 맞춤 높이 클릭
            )

            // 항목을 Long 클릭 시 메뉴를 띄워 준다
            itemsBinding.root.setOnCreateContextMenuListener { menu, v, menuInfo ->

            }

        }
            fun onClick(manager: FragmentManager) {
                // 화면 전환 코드 작성
                manager
                    .beginTransaction()
                    .replace(R.id.containerMain, InfoFragment())
                    .addToBackStack(FragmentName.INFO_FRAGMENT.str)
                    .commit()
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        return DataViewHolder(ItemsBinding.inflate(LayoutInflater.from(parent.context)), manager)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.itemsBinding.textViewItemsName.text = "이름 : ${dataSet[position].studentName}"
        holder.itemsBinding.textViewItemsGrade.text = "학년 : ${dataSet[position].studentGrade} 학년"

        // 클릭 리스너 설정
        holder.itemView.setOnClickListener {
            // 화면 전환
            holder.onClick(manager)
        }
    }


}

