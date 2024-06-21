package com.example.android_review02_kshn379

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android_review02_kshn379.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    var filteredList : MutableList<Research> = mutableListOf()
    val dataList: MutableList<Research> = mutableListOf()
    lateinit var adapter: ResearchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 바인딩 초기화
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // 뷰 설정
        settingView()
        // 이벤트 설정
        settingEvent()

    }

    // 이벤트 설정
    fun settingEvent() {
        binding.apply {
            // 버튼 클릭시 작동
            buttonMainAdd.setOnClickListener {
                // 유효성 검사
                val resultCheck = validateInput()

                if (resultCheck) {
                    val name = textInputEditTextMainName.text.toString()
                    // 리스트로 만든다
                    val data = Research(name)
                    // datalist에 담아준다
                    dataList.add(data)

                    // 어댑터 연결
                    recyclerViewMain.adapter = ResearchAdapter(dataList)

                    // 어댑터에 변경을 알린다
                    recyclerViewMain.adapter?.notifyDataSetChanged()

                    // 어댑터 연결
                    adapter = ResearchAdapter(dataList)

                    // 입력 요소 초기화
                    textInputEditTextMainName.text = null
                }
            }

            textInputEditTextMainSearch.addTextChangedListener {
                filteredList.clear()
                filteredList.addAll(dataList.filter {

                    it.name.contains(textInputEditTextMainSearch.text.toString(), true)

                })
                // 어댑터 연결
                recyclerViewMain.adapter = ResearchAdapter(filteredList)

                // 어댑터 변경 알림
                recyclerViewMain.adapter?.notifyDataSetChanged()
            }
        }
    }



    // 뷰 설정
    fun settingView() {
        binding.apply {
            recyclerViewMain.apply {
                adapter = ResearchAdapter(dataList)
                layoutManager = LinearLayoutManager(context)
            }
        }

    }



    // 유효성 검사
    fun validateInput(): Boolean {
        var resultName = false
        binding.apply {
            val name = textInputEditTextMainName.text.toString()

            if (name.isNotEmpty()) {
                resultName = true
            } else {
                resultName = false
            }
        }
        return resultName
    }
}
