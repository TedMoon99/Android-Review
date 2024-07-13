package com.example.android_review05_tedmoon.adapter

import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android_review05_tedmoon.R
import com.example.android_review05_tedmoon.databinding.RowMainBinding
import com.example.android_review05_tedmoon.fragment.ShowFragment
import com.example.android_review05_tedmoon.model.ScoreInfo
import com.example.android_review05_tedmoon.utils.FragmentName

class CustomAdapter(val dataSet: ArrayList<ScoreInfo>, val menuInflater: MenuInflater, val manager: FragmentManager): RecyclerView.Adapter<CustomAdapter.CustomViewHolder>() {

    class CustomViewHolder(rowMainBinding: RowMainBinding, menuInflater: MenuInflater): RecyclerView.ViewHolder(rowMainBinding.root){
        val rowMainBinding: RowMainBinding
        init {
            this.rowMainBinding = rowMainBinding

            // 클릭 영역 자체를 항목의 전체가 되도록 설정한다
            rowMainBinding.root.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, // 가로 전체
                ViewGroup.LayoutParams.WRAP_CONTENT // 세로 전체
            )

            // RecyclerView의 항목에 컨텍스트 메뉴를 설정해준다
            rowMainBinding.root.setOnCreateContextMenuListener { menu, v, menuInfo ->
                // 메뉴의 헤더
                menu?.setHeaderTitle("삭제하시겠습니까?")
                // 메뉴를 구성한다
                menuInflater.inflate(R.menu.menu_customadapter_contextmenu, menu)
                // 각 메뉴 아이템을 추출하여 리스너를 설정해준다
                menu?.findItem(R.id.menuItem_adapter_delete)?.setOnMenuItemClickListener {
                    // DB 삭제 구현
                    // ### DB 연동하고 나면 곧 합니다~ ###
                    true
                }
            }
        }

        // Adapter 항목 클릭 시 화면 이동
        fun onClicked(manager: FragmentManager){
            manager
                .beginTransaction()
                .replace(R.id.containerMain, ShowFragment())
                .addToBackStack(FragmentName.SHOW_FRAGMENT.str)
                .commit()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        return CustomViewHolder(RowMainBinding.inflate(LayoutInflater.from(parent.context)), menuInflater)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.rowMainBinding.textViewRowMainName.text = "이름 : ${dataSet[position].name}"
        holder.rowMainBinding.textViewRowMainGrade.text = "학년 : ${dataSet[position].grade}학년"

        // 항목 클릭 시 화면 전환
        holder.itemView.setOnClickListener {
            holder.onClicked(manager)
        }
    }
}