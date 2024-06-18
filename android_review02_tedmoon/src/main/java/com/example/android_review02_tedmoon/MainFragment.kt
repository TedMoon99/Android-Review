package com.example.android_review02_tedmoon

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android_review02_tedmoon.databinding.FragmentMainBinding
import com.google.android.material.divider.MaterialDividerItemDecoration

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    // RecyclerView에 전달할 데이터리스트
    var dataList: MutableList<String> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMainBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        settingView() // View 설정
        settingEvent() // Event 설정

    }
    // View 설정
    fun settingView(){
        binding.apply {
            val context = requireContext()
            // 구분선 추가
            val deco = MaterialDividerItemDecoration(context, LinearLayoutManager.VERTICAL).apply {
                // 마지막 줄은 구분선 제거
                isLastItemDecorated = false
            }

            // recyclerView 설정
            recyclerViewMain.apply {
                // adapter 연결
                adapter = CustomAdapter(dataList)
                // layoutManager 연결
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                // 구분선 추가
                addItemDecoration(deco)
            }

        }
    }
    // Event 설정
    fun settingEvent(){
        binding.apply {

        }
    }
}