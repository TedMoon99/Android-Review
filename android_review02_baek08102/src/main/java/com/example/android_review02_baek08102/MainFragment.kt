package com.example.android_review02_baek08102

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android_review02_baek08102.databinding.FragmentMainBinding
import com.google.android.material.divider.MaterialDividerItemDecoration

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private val dataList: MutableList<String> = mutableListOf()
    private val filteredList: MutableList<String> = mutableListOf()

    // 이곳엔 바인딩과 관련된 것들 선언
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMainBinding.inflate(inflater)

        return binding.root
    }

    // 이곳엔 일반적 함수들 호출
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        settingView()
        settingEvent()

    }

    // 뷰 설정 위한 함수
    fun settingView() {
        binding.apply {
            val context = requireContext()
            // 현재 프래그먼트의 Context 가져옴

            // 구분선 생성
            val deco = MaterialDividerItemDecoration(context, LinearLayoutManager.VERTICAL).apply {
                // 마지막 구분선 제거
                isLastItemDecorated = false
            }
            // 리싸이클러뷰 연결
            recyclerViewMain.apply {
                layoutManager = LinearLayoutManager(context)
                addItemDecoration(deco)
            }
        }
    }

    // 이벤트 설정 위한 함수

    fun settingEvent() {
        binding.apply {
            // 추가 버튼 클릭 시
            buttonMainAdd.setOnClickListener {
                val resultName = validateInput()

                Log.d("동수", "버튼 클릭")

                if (resultName) { // 유효성검사 통과 시
                    val name = textInputEditTextMainName.text.toString()

                    // 데이터 입력
                    dataList.add(name)

                    // 어댑터 연결
                    recyclerViewMain.adapter = MyAdapter(dataList)

                    // 어댑터에게 데이터 변경 알림
                    recyclerViewMain.adapter?.notifyDataSetChanged()

                    //입력 요소 초기화
                    textInputEditTextMainName.text = null
                }
            }

            // 텍스트 실시간 리스너
            textInputEditTextMainSearch.addTextChangedListener { search ->
                val newFilteredList = dataList.filter { it.contains(search.toString()) }

                //filteredList 안의 내용 삭제
                filteredList.clear()
                //filteredList 안에 newFilteredList의 내용 저장
                filteredList.addAll(newFilteredList)

                recyclerViewMain.apply {
                    // 어댑터 연결
                    adapter = MyAdapter(filteredList)
                    // 어댑터 변경 알림
                    adapter?.notifyDataSetChanged()
                }
            }
        }
    }

    // 유효성 검사 위한 함수
    fun validateInput(): Boolean {
        var resultName = false
        binding.apply {
            // 입력된 이름 받아온다
            val name = textInputEditTextMainName.text.toString()

            if (name.isNotEmpty()) { // 값이 입력 되었고
                if (name.length in 2 until 6) { // 입력값이 2자 이상 5자 이하라면
                    // 오류 해제
                    textInputLayoutMainName.error = null
                    resultName = true
                } else {
                    // 오류 설정
                    textInputLayoutMainName.error = "올바른 이름 형식이 아닙니다."
                    resultName = false
                }
            } else {
                //오류 설정
                textInputLayoutMainName.error = "이름을 입력해주세요."
                resultName = false
            }
            return resultName
        }
    }
}