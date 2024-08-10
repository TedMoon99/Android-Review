package com.example.android_review06_baek08102.adapter

import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.example.android_review06_baek08102.R
import com.example.android_review06_baek08102.databinding.FragmentMainBinding
import com.example.android_review06_baek08102.databinding.RowMainBinding
import com.example.android_review06_baek08102.fragment.ShowFragment
import com.example.android_review06_baek08102.model.AnimalData
import com.example.android_review06_baek08102.utils.FragmentName
import com.example.android_review06_baek08102.viewmodel.ShowViewModel

class CustomAdapter(
    private val dataList: ArrayList<AnimalData>,
    private val fragmentManager: FragmentManager,
    private val viewModel: ShowViewModel
) : RecyclerView.Adapter<CustomAdapter.CustomViewHolder>() {

    inner class CustomViewHolder(
        viewModel: ShowViewModel,
        rowBinding: RowMainBinding
    ) : RecyclerView.ViewHolder(rowBinding.root) {
        val rowBinding: RowMainBinding

        init {
            this.rowBinding = rowBinding
        }

        // recycler item 클릭 리스너 함수
        fun clickListener(
            viewModel: ShowViewModel,
            fragmentManager: FragmentManager,
            animalIdx: Int
        ) {
            viewModel.updateClickedIndex(animalIdx)

            fragmentManager.beginTransaction()
                .replace(R.id.main_container, ShowFragment())
                .addToBackStack(FragmentName.Show_Fragment.name)
                .commit()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {

        val rowBinding = RowMainBinding.inflate(LayoutInflater.from(parent.context))
        val viewHolder = CustomViewHolder(viewModel, rowBinding)

        return viewHolder
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.rowBinding.rowName.text = "동물 이름 : ${dataList[position].name}"
        holder.rowBinding.rowAge.text = "동물 나이 : ${dataList[position].age}"

        // 아이템 클릭 시
        holder.itemView.setOnClickListener {
            // 사용자 정의 클릭 리스너 함수 통하여 클릭한 item의 animalIdx 전달
            holder.clickListener(viewModel, fragmentManager, dataList[position].animalIdx)
        }
    }
}