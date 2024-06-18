package com.example.android_review02_tedmoon

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android_review02_tedmoon.databinding.FragmentMainBinding
import com.google.android.material.divider.MaterialDividerItemDecoration

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    // RecyclerView에 전달할 데이터리스트
    var dataList: MutableList<String> = mutableListOf()
    // 검색 결과를 나타낼 새로운 목록
    private val filteredList: MutableList<String> = mutableListOf()

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
            // 버튼 클릭시 이름 항목 RecyclerView에 추가
            buttonMainAdd.setOnClickListener {
                val resultCheck = validateInput()

                if (resultCheck){ // 유효성 검사 통과시
                    // 이름 값을 받아온다
                    val name = textInputEditTextMainName.text.toString()
                    // dataList에 값을 넣어준다
                    dataList.add(name)
                    // Adapter에게 값이 변경되었음을 알려준다
                    recyclerViewMain.adapter?.notifyDataSetChanged()

                    // textField 이름을 초기화 해준다
                    textInputEditTextMainName.text = null
                }
            }

            // 텍스트 체인지 리스너
            // 값이 변화할 때마다 작동하는 리스너
            textInputEditTextMainSearch.addTextChangedListener { search ->
                // 비어있는 경우 모든 항목이 나오게 한다
                if (search == null){
                    recyclerViewMain.apply {

                        // adapter dataList 전송
                        adapter = CustomAdapter(dataList)

                        // RecyclerView의 어댑터에게 변경을 알림
                        adapter?.notifyDataSetChanged()
                    }
                }
                // 비어있지 않은 경우
                else {
                    // adapter filteredList 전송
                    recyclerViewMain.adapter = CustomAdapter(filteredList)

                    // 검색어에 따라 필터링하여 새로운 목록 생성
                    val newFilteredList = dataList.filter {
                        it.contains(search.toString())
                    }

                    // 이전 검색 결과 목록을 제거
                    filteredList.clear()

                    // 새롭게 필터링된 목록을 리스트에 전송
                    filteredList.addAll(newFilteredList)

                    // RecyclerView에 새로운 필터링 목록을 전송하고 어댑터에게 변경 알림
                    recyclerViewMain.adapter?.notifyDataSetChanged()
                }
            }
        }
    }

    // 유효성 검사
    fun validateInput(): Boolean{
        var resultName = false
        binding.apply {
            // 입력된 이름을 받아온다
            val name = textInputEditTextMainName.text.toString()

            if (name.isNotEmpty()){ // 이름이 비어있지 않다면
                if (name.length in 2 until 6){ // 정상적인 입력
                    textInputLayoutMainName.error = null
                    resultName = true
                } else{ // 비정상적인 입력
                    textInputLayoutMainName.error = "잘못된 입력입니다. 이름은 2자 ~ 5자로 입력해주세요"
                    resultName = false
                }
            } else { // 이름이 비어있는 경우
                textInputLayoutMainName.error = "이름을 입력해주세요"
                resultName = false
            }
        }
        return resultName
    }
}