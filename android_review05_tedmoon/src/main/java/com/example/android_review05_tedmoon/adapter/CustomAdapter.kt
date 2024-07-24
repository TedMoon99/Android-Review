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
import com.example.android_review05_tedmoon.viewmodel.MainViewModel

class CustomAdapter(private val dataSet: ArrayList<ScoreInfo>?, val menuInflater: MenuInflater, val manager: FragmentManager, val viewModel: MainViewModel): RecyclerView.Adapter<CustomAdapter.CustomViewHolder>() {

    inner class CustomViewHolder(val rowMainBinding: RowMainBinding): RecyclerView.ViewHolder(rowMainBinding.root){
        init {
            // 클릭 영역 자체를 항목의 전체가 되도록 설정한다
            rowMainBinding.root.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, // 가로 전체
                ViewGroup.LayoutParams.WRAP_CONTENT // 세로 전체
            )

            // RecyclerView의 항목에 컨텍스트 메뉴를 설정해준다
            // menu : 생성된 컨텍스트 메뉴 객체
            // v : 클릭된 뷰
            // menuInfo : 메뉴에 대한 추가 정보를 제공

            rowMainBinding.root.setOnCreateContextMenuListener { menu, v, menuInfo ->
                // 메뉴를 구성한다
                menuInflater.inflate(R.menu.menu_customadapter_contextmenu, menu)
                // 각 메뉴 아이템을 추출하여 리스너를 설정해준다
                menu?.findItem(R.id.menuItem_adapter_delete)?.setOnMenuItemClickListener { menuItem ->
                    // DB 삭제 구현
                    val position = adapterPosition // adapterPosition : 현재 항목의 위치를 얻음
                    if (position != RecyclerView.NO_POSITION){ // position이 유효한 지 = RecyclerView.NO_POSITION이 아닌지 확인
                        // 선택한 데이터의 ScoreInfo에 접근
                        val scoreInfo = dataSet?.get(position)
                        if (scoreInfo != null) {
                            viewModel.removeData(scoreInfo.studentIdx)
                        }

                        dataSet?.removeAt(position) // dataSet에서 position번째 데이터 제거
                        notifyItemRemoved(position) // position번째 데이터 삭제를 알림
                    }
                    true// true : 클릭 이벤트가 처리되었음을 나타냄
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
        return CustomViewHolder(RowMainBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun getItemCount(): Int {
        return dataSet?.size ?: 0
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.rowMainBinding.textViewRowMainName.text = "이름 : ${dataSet?.get(position)?.name}"
        holder.rowMainBinding.textViewRowMainGrade.text = "학년 : ${dataSet?.get(position)?.grade}학년"

        // 항목 클릭 시 화면 전환
        holder.itemView.setOnClickListener {
            holder.onClicked(manager)
        }
    }
}