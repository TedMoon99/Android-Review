package com.example.android_review05_kshn379

import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android_review05_kshn379.databinding.RowBinding

class StudentAdapter(
    private val dataSet: ArrayList<ManageInfo>, private val manager: FragmentManager
) : RecyclerView.Adapter<StudentAdapter.StoreViewHolder>() {

    // ViewHolder class 생성
    class StoreViewHolder(rowBinding: RowBinding) : RecyclerView.ViewHolder(rowBinding.root) {
        val rowBinding: RowBinding

        init {
            this.rowBinding = rowBinding

            // 항목의 클릭 영역을 항목 자체로 설정한다
            rowBinding.root.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, // 가로 전체 너비 클릭
                ViewGroup.LayoutParams.WRAP_CONTENT // 세로 맞춤 높이 클릭
            )

            // 항목을 Long 클릭 시 메뉴를 띄워 준다
            rowBinding.root.setOnCreateContextMenuListener { menu, v, menuInfo ->
                menu.apply {
                    setHeaderTitle("데이터가 삭제됩니다")
                    // ContextMenu 항목 클릭 시 데이터 삭제 구현
                    add("삭제").setOnMenuItemClickListener {
                        ManageInfo.
                    }
                }
            }
        }


        // ViewHolder(리사이클러 뷰 아이템) 클릭 시 작동
        fun onClicked(manager: FragmentManager, position: Int) {
            val infoFragment = InfoFragment().apply {
                val data = Bundle().apply {
                    putInt("position", position)
                }
                arguments = data
            }
            // 화면 전환
            manager
                .beginTransaction()
                .replace(R.id.containerMain, infoFragment)
                .addToBackStack(FragmentName.INFO_FRAGMENT.str)
                .commit()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreViewHolder {
        // Binding 적용
        val rowBinding = RowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StoreViewHolder(rowBinding)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    // Recycler View 화면 설정
    override fun onBindViewHolder(holder: StoreViewHolder, position: Int) {
        holder.rowBinding.textViewRowName.text = "이름 : ${dataSet[position].studentName}"
        holder.rowBinding.textViewRowGrade.text = "학년 : ${dataSet[position].studentGrade}학년"
        // Click Listener 설정
        holder.itemView.setOnClickListener {
            // 화면 전환
            holder.onClicked(manager, position)
        }
    }
}