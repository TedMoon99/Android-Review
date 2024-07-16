package com.example.android_review05_kshn379

import android.os.Bundle
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android_review05_kshn379.databinding.RowBinding

class StudentAdapter (
    private val dataSet: ArrayList<ManageInfo>, val manager: FragmentManager
) : RecyclerView.Adapter<StudentAdapter.StoreViewHolder>() {
    // ViewHolder class 생성
    class StoreViewHolder(var rowBinding: RowBinding): RecyclerView.ViewHolder(rowBinding.root),
        View.OnCreateContextMenuListener {
        init {
            itemView.setOnCreateContextMenuListener(this)
            this.rowBinding = rowBinding

//            // 항목의 클릭 영역을 항목 자체로 설정한다
//            rowBinding.root.layoutParams = ViewGroup.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT, // 가로 전체 너비 클릭
//                ViewGroup.LayoutParams.WRAP_CONTENT // 세로 맞춤 높이 클릭
//            )

//            // 항목을 Long 클릭 시 메뉴를 띄워 준다
//            rowBinding.root.setOnCreateContextMenuListener { menu, v, menuInfo ->
//                menu?.add()
//            }

        }
        // 리사이클러 뷰 메뉴 설정 및 데이터 연결 예정~~~
        // ContextMenu(RecyclerView 아이템) 클릭 시 설정
        override fun onCreateContextMenu(
            menu: ContextMenu?,
            v: View?,
            menuInfo: ContextMenu.ContextMenuInfo?
        ) {
            menu?.setHeaderTitle()
            menuInflater.inflate(R.menu.contextmenu_delete, menu)
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