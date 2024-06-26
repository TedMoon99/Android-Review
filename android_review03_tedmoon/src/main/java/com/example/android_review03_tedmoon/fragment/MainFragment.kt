package com.example.android_review03_tedmoon.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android_review03_tedmoon.R
import com.example.android_review03_tedmoon.adapter.CustomAdapter
import com.example.android_review03_tedmoon.databinding.FragmentMainBinding
import com.example.android_review03_tedmoon.model.ScoreInfo
import com.example.android_review03_tedmoon.utils.FragmentName
import com.google.android.material.divider.MaterialDividerItemDecoration

class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
    val dataList: MutableList<ScoreInfo> = mutableListOf()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMainBinding.inflate(inflater)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // View 설정
        settingView()
        // Event 설정
        settingEvent()
    }

    fun settingEvent(){
        binding.apply {
            // 학생 정보 입력 버튼 리스너 설정
            buttonMainAdd.setOnClickListener {
                parentFragmentManager.apply {
                    beginTransaction() // 트랜잭션 생성
                        .replace(R.id.containerMain, SubFragment1()) // containerMain SubFragment2 출력
                        .addToBackStack(FragmentName.SUB_FRAGMENT1.name) // 백스택에 추가
                        .commit() // 실행
                }
                childFragmentManager.apply {
                    // SubFragment가 종료되고 돌아오면 실행되는 콜백 리스너
                    setFragmentResultListener("SubFragment1"){ requestKey, bundle ->
                        val result = bundle.getParcelable<ScoreInfo>("scoreInfo")
                        result?.let { data ->
                            Log.d("test1234", "MainFragment에서 받아온 데이터 : ${data}")
                            // dataList에 정보를 저장한
                            dataList.add(data)
                            // adapter에게 데이터가 변경되었음을 알린다
                            recyclerViewMain.adapter?.notifyDataSetChanged()
                        }
                    }
                }
            }

            // 총점 및 평균
            buttonMainTotalAverage.setOnClickListener {
                parentFragmentManager.apply {
                    beginTransaction() // 트랜잭션 생성
                        .replace(R.id.containerMain, SubFragment2()) // containerMain SubFragment2 출력
                        .addToBackStack(FragmentName.SUB_FRAGMENT2.name) // 백스택에 추가
                        .commit() // 실행
                }
            }
        }

    }

    fun settingView(){
        binding.apply {
            recyclerViewMain.apply {
                // 구분선 생성
                val deco = MaterialDividerItemDecoration(context, LinearLayoutManager.VERTICAL)
                // 어댑터 연결
                adapter = CustomAdapter(dataList)
                // 레이아웃 매니저 연결
                layoutManager = LinearLayoutManager(context)
                // 구분선 적용
                addItemDecoration(deco)
            }
        }

    }
}