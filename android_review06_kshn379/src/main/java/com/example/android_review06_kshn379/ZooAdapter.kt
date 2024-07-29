package com.example.android_review06_kshn379

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android_review06_kshn379.databinding.RowBinding

class ZooAdapter(
    private val dataSet: ArrayList<ZooInfo>, private val manager: FragmentManager
) : RecyclerView.Adapter<ZooAdapter.AnimalViewHolder>() {
    // ViewHolder Class 생성
    inner class AnimalViewHolder(rowBinding: RowBinding) :
        RecyclerView.ViewHolder(rowBinding.root) {
        val rowBinding: RowBinding

        init {
            this.rowBinding = rowBinding

            // 항목의 클릭 영역을 항목 자체로 설정
            rowBinding.root.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }

        // RecyclerView Item 클릭 시 작동
        fun onClick(manager: FragmentManager, position: Int) {
            val infoFragment = InfoFragment().apply {
                // Bundle 객체 생성하여 data(position)를 담는다
                val data = Bundle().apply {
                    // position 값을 키로 번들 추가
                    putInt("position", position)
                }
                // InfoFragment 에 데이터 전달하기 위해 arguments 로 설정
                arguments = data
            }
            // 화면 전환
            manager.beginTransaction()
                .replace(R.id.containerMain, infoFragment)
                .addToBackStack(FragmentName.INFO_FRAGMENT.str)
                .commit()
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ZooAdapter.AnimalViewHolder {
        // Binding 적용
        val rowBinding = RowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AnimalViewHolder(rowBinding)
    }

    override fun onBindViewHolder(holder: ZooAdapter.AnimalViewHolder, position: Int) {
        holder.rowBinding.textViewRowName.text = "이름 : ${dataSet[position].animalName}"
        holder.rowBinding.textViewRowType.text = "종류 : ${dataSet[position].animalType}"

        // 종류에 따라 이미지 변경 설정
        val imageChange = when(dataSet[position].animalType) {
            "사자" -> R.drawable.lion
            "호랑이" -> R.drawable.tiger
            "기린" -> R.drawable.giraffe
            else -> R.drawable.animals // 기본 이미지 설정
        }
        holder.rowBinding.imageViewRowAnimal.setImageResource(imageChange)

        // Click Listener 설정
        holder.itemView.setOnClickListener {
            // 화면 전환
            holder.onClick(manager, position)
        }
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }
}

