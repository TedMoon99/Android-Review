package com.example.android_review05_kshn379

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.core.view.isInvisible
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android_review05_kshn379.databinding.FragmentMainBinding
import com.example.android_review05_kshn379.databinding.RowBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class StudentAdapter(
    private val dataSet: ArrayList<ManageInfo>, private val manager: FragmentManager
) : RecyclerView.Adapter<StudentAdapter.StoreViewHolder>() {

    // ViewHolder class 생성
    inner class StoreViewHolder(rowBinding: RowBinding) : RecyclerView.ViewHolder(rowBinding.root) {
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
                        // 현재 항목의 위치를 가져 온다
                        val position = adapterPosition
                        // 위치가 유효 한지 확인 한다
                        if (position != RecyclerView.NO_POSITION) {
                            // 현재 항목의 studentIdx 를 가져 온다
                            val studentIdx = dataSet[position].studentIdx
                            // deleteItem 함수 호출 하여 항목 삭제 표시 한다
                            deleteItem(studentIdx)
                        }
                        true
                    }
                }
            }
        }

        // 데이터 항목 삭제 하는 함수 구현 하기
        private fun deleteItem(studentIdx: Int) {
            // IO 스레드 - 네트워크나 파일 입출력 작업 처럼 시간이 걸리는 작업 처리 하는 데 적합
            CoroutineScope(Dispatchers.IO).launch {
                // 예외 처리
                try {
                    // 비동기 작업 - firestore 에서 studentIdx 해당 하는 항목의 dataState를 false로 업데이트
                    AddDao.removeItem(studentIdx, false)
                    // 메인 Dispatchers에서 UI 변경 처리
                    withContext(Dispatchers.Main) {
                        // dataSet에서 studentIdx 항목 모두 제거
                        dataSet.removeAll { it.studentIdx == studentIdx }
                        // RecyclerView 데이터 변경 알림
                        notifyDataSetChanged()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    Log.e("StudentAdapter", "아이템 데이터 삭제 실패 : ${e.message}")
                }
            }
        }


        // ViewHolder(리사이클러 뷰 아이템) 클릭 시 작동
        fun onClicked(manager: FragmentManager, position: Int) {
            val infoFragment = InfoFragment().apply {
                // Bundle 객체를 생성 하여 data(position)를 담는다
                val data = Bundle().apply {
                    // position 값을 키로 번들 추가 한다
                    putInt("position", position)
                }
                // InfoFragment 에 데이터를 전달 하기 위해 arguments 로 설정
                arguments = data
            }
            // 화면 전환
            manager
                .beginTransaction() // 트랜잭션 실행
                .replace(R.id.containerMain, infoFragment)
                .addToBackStack(FragmentName.INFO_FRAGMENT.str)
                .commit() // 실행
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