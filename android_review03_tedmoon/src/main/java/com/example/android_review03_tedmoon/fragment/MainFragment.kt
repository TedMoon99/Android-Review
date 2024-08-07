package com.example.android_review03_tedmoon.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
    val dataList: ArrayList<ScoreInfo> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // 데이터 받아오기
        gettingData()
        // View 설정
        settingView()
        // Event 설정
        settingEvent()
    }

    fun settingEvent(){
        binding.apply {

            // 학생 정보 입력 버튼 리스너 설정
            buttonMainAdd.setOnClickListener {
                // 화면 이동
                moveFragment(FragmentName.SUB_FRAGMENT1)
            }

            // 총점 및 평균
            buttonMainTotalAverage.setOnClickListener {
                if (dataList.size != 0){ // 입력된 내용이 있는 경우
                    // 화면 이동
                    moveFragment(FragmentName.SUB_FRAGMENT2)
                } else {
                    // 입력된 내용이 없는 경우
                    val toast = Toast.makeText(context, "학생 정보를 먼저 입력해주세요", Toast.LENGTH_SHORT)
                    toast.show()
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
                adapter = CustomAdapter(dataList, parentFragmentManager)
                // 레이아웃 매니저 연결
                layoutManager = LinearLayoutManager(context)
                // 구분선 적용
                addItemDecoration(deco)
            }
        }

    }
    fun moveFragment(name: FragmentName){
        when(name){

            // 입력 추가 화면으로 이동
            FragmentName.SUB_FRAGMENT1 -> {
                parentFragmentManager.apply {
                    beginTransaction() // 트랜잭션 생성
                        .replace(R.id.containerMain, SubFragment1()) // containerMain -> SubFragment1 출력
                        .addToBackStack(FragmentName.SUB_FRAGMENT1.name) // 백스택에 추가
                        .commit() // 실행
                }

            }

            // 총점 및 평균 화면으로 이동
            FragmentName.SUB_FRAGMENT2 -> {
                val data = dataList
                val subFragment2 = SubFragment2().apply {
                    arguments = Bundle().apply {
                        putParcelableArrayList("dataList", data)
                    }
                }

                parentFragmentManager.apply {
                    beginTransaction() // 트랜잭션 생성
                        .replace(R.id.containerMain, subFragment2) // containerMain -> SubFragment2 출력
                        .addToBackStack(FragmentName.SUB_FRAGMENT2.name) // 백스택에 추가
                        .commit() // 실행
                }
            }

            else -> {
                Log.d("test1234", "잘못된 이동입니다")
            }
        }
    }

    fun gettingData(){

        // SubFragment1에서 데이터를 받아온다
        setFragmentResultListener("Input Complete"){ requestKey, bundle ->
            val result = bundle.getParcelable<ScoreInfo>("scoreInfo")
            result?.let { data ->
                // dataList에 정보를 저장한
                dataList.add(data)
                // adapter에게 데이터가 변경되었음을 알린다
                binding.recyclerViewMain.adapter?.notifyDataSetChanged()
            }
        }
    }
}