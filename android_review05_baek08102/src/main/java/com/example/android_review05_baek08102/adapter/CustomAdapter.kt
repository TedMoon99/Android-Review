package com.example.android_review05_baek08102.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.view.MenuInflater
import androidx.core.view.contains
import androidx.core.view.get
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.example.android_review05_baek08102.model.StudentData
import com.example.android_review05_baek08102.R
import com.example.android_review05_baek08102.dao.StudentDao
import com.example.android_review05_baek08102.dao.StudentDao.Companion.deleteData
import com.example.android_review05_baek08102.databinding.RowMainBinding
import com.example.android_review05_baek08102.fragment.MainFragment
import com.example.android_review05_baek08102.fragment.ShowFragment
import com.example.android_review05_baek08102.utils.FragmentName
import com.example.android_review05_baek08102.viewmodel.ShowViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CustomAdapter(
    private val dataList: ArrayList<StudentData>,
    private val fragmentManager: FragmentManager,
    private val menuInflater: MenuInflater,
    private val viewModel: ShowViewModel // 어댑터의 인자로 ShowViewModel
) : RecyclerView.Adapter<CustomAdapter.CustomViewHolder>() {
    class CustomViewHolder(
        rowBinding: RowMainBinding,
        menuInflater: MenuInflater,
        viewModel: ShowViewModel // ViewHolder의 인자로 넘겨받을 ShowViewModel
    ) : RecyclerView.ViewHolder(rowBinding.root) {
        val rowBinding: RowMainBinding // 데이터 바인딩 사용 위한 row_main.xml 바인딩 선언

        init {
            this.rowBinding = rowBinding

            // rowBinding의 view, 즉 각 아이템의 출력 크기 지정
            // 너비는 부모 뷰, 즉 recyclerView와 맞추고
            // 높이는 각 아이템의 높이만큼
            // 이로써 rowBinding의 클릭 영역이 항목의 전체가 되도록 설정
            this.rowBinding.root.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )

            // 리사이클러뷰 아이템의 컨텍스트 메뉴 설정
            rowBinding.root.setOnCreateContextMenuListener { menu, v, menuInfo ->
                // viewHolder의 생성자 인수로 넘겨받은 menuInflater inflate
                menuInflater.inflate(R.menu.menu_recycler_item, menu)

                // 아이템 삭제하는 menu item의 클릭 리스너 설정
                menu?.findItem(R.id.menu_recyclerItem_delete)?.setOnMenuItemClickListener {

                    val position = adapterPosition // 사용자가 클릭한 아이템의 포지션

                    // viewModel 함수 호출

                    viewModel.updateClickedPosition(position) // viewModel에 position 데이터 전달
                    // position 데이터 update 시 MainFragment에서 변경 관측하여 해당 position의 아이템 삭제

                    viewModel.deleteDataOnPosition() // 위에서 전달한 포지션의 데이터 삭제 함수
                    // viewModel을 통하여 Dao내의 DB 데이터 삭제 함수까지 호출

                    Log.d("deleting data process", "in Adapter -> clicked position : $position")

                    true
                }

            }
        }

        // 아이템 짧은 클릭 시 호출되는 클릭 리스너
        fun clickListener(
            viewModel: ShowViewModel,
            fragmentManager: FragmentManager,
            index: Int
        ) {
            viewModel.updateSelectediIndex(index) // ShowFragment에게 클릭한 아이템의 studentIdx 데이터 전달하는 함수

            // 화면 전환
            fragmentManager
                .beginTransaction()
                .replace(R.id.main_containerView, ShowFragment())
                .addToBackStack(FragmentName.Show_Fragment.name)
                .commit()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {

        val rowBinding = RowMainBinding.inflate(LayoutInflater.from(parent.context))
        val viewHolder = CustomViewHolder(rowBinding, menuInflater, viewModel)

        return viewHolder
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.rowBinding.mainRowName.text = "이름 : ${dataList[position].name}"
        holder.rowBinding.mainRowGrade.text = "학년 : ${dataList[position].grade}"

        holder.itemView.setOnClickListener {
            holder.clickListener(viewModel, fragmentManager, dataList[position].studentIdx)
            // 이곳에서 ShowViewModel 통해 ShowFragment로 클릭한 아이템의 studentIdx 데이터 전달
        }
    }
}