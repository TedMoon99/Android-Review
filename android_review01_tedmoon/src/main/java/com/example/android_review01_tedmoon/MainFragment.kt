package com.example.android_review01_tedmoon

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.android_review01_tedmoon.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding

    // 바인딩 괸련된 코드는 onCreateView에 작성해주는 것이 좋고,
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMainBinding.inflate(inflater)
        return binding.root
    }

    // View와 관련된 코드는 onViewCreated에 작성해주는 것이 좋다. - 생명주기(lifecycler)과 관련이 있다
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    // 이벤트 처리
    fun settingEvent(){
        binding.apply {

            // 버튼 클릭 리스너
            buttonMainComplete.setOnClickListener {
                val inputResult = validateInput()

                if (inputResult){ // 유효성 검사 결과가 true이면
                    // RecyclerView 어댑터에 데이터를 전송해준다.

                }


            }

        }
    }

    // 입력 유효성 검사
    fun validateInput(): Boolean{
        var resultID = false
        var resultName = false
        binding.apply {
            // 입력을 받아온다
            val id = editTextMainId.text.toString()
            val name = editTextMainName.text.toString()

            // id 관련 오류 처리
            if (id.isNotEmpty()){ // id 입력을 했다면
                if (id.length < 9){ // 입력한 id가 8자 이하라면
                    editTextMainId.error = "아이디는 최소한 9자 이상 입력해주세요"
                    resultID = false
                } else { // 입력 확인
                    editTextMainId.error = null // Error 제거
                    resultID = true
                }

            } else { // id 입력을 안 했다면
                editTextMainId.error = "id를 입력해주세요"
                resultID = false
            }

            // 이름 관련 오류 처리
            if (name.isNotEmpty()){ // 이름 입력을 했다면
                if (name.length < 2){ // 입력한 이름이 1자 이하라면
                    editTextMainName.error = "이름은 최소한 2자 이상 입력해주세요"
                    resultName = false
                } else { // 입력 확인
                    editTextMainName.error = null // Error 제거
                    resultName = true
                }

            } else { // 이름 입력을 안 했다면
                editTextMainName.error = "이름을 입력해주세요"
                resultID = false
            }
        }

        return resultID && resultName
    }
}