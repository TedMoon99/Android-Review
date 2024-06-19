package com.example.android_review01_kshn379

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android_review01_kshn379.databinding.ActivityMainBinding
import com.google.android.material.divider.MaterialDividerItemDecoration

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    val dataList: MutableList<Profiles> = mutableListOf()
    // [ [히히, 안녕], [안녕, 히히] ]

    /*
    ### ID 설정 규칙
    1. what_where_desription
    : 무엇을 / 어디에서 / 뭐에 대한 내용인지
    :
    2. where_what_description
    : 어디에서 / 무엇이고 / 뭐에 대한 내용인지
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
            buttonMainCheck.setOnClickListener {
                // 유효성 검사
                val resultCheck = validateInput()

                if (resultCheck){
                    val id = textInputEditTextMainId.text.toString()
                    val name = textInputEditTextMainName.text.toString()
                    val pwd = textInputEditTextMainPwd.text.toString()
                    // 리스트로 만든다
                    val data = Profiles(0, name, id, pwd)
                    // datalist에 담아준다
                    dataList.add(data)

                    // 어댑터에 변경을 알린다
                    recyclerViewMain.adapter?.notifyDataSetChanged()

                }
            }
        }
    }
    // 뷰 설정
    fun settingView() {
        binding.apply {
            recyclerViewMain.apply {
                adapter = ProfileAdapter(dataList)
                layoutManager = LinearLayoutManager(context)
            }
        }

    }

    // 유효성 검사
    fun validateInput(): Boolean {
        var resultId = false
        var resultName = false
        var resultPwd = false
        binding.apply {
            val id = textInputEditTextMainId.text.toString()
            val name = textInputEditTextMainName.text.toString()
            val pwd = textInputEditTextMainPwd.text.toString()

            if (id.isNotEmpty()){
                resultId = true
            } else {
                resultId = false
            }

            if (name.isNotEmpty()){
                resultName = true
            } else {
                resultName = false
            }

            if (pwd.isNotEmpty()){
                resultPwd = true
            } else {
                resultPwd = false
            }
        }
        return resultId && resultName && resultPwd
    }
}