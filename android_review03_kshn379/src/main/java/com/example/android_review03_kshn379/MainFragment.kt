package com.example.android_review03_kshn379

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android_review03_kshn379.databinding.FragmentMainBinding
import com.example.android_review03_kshn379.databinding.ItemStudentsBinding

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    val studentInfoList = mutableListOf<Student>()
    lateinit var adapter: StudentAdapter

    // binding 관련 초기화를 해준다
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    // 일반 함수를 호출해준다
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // View
        settingView()
        // Event
        settingEvent()
    }

    // View 설정
    fun settingView(){
        binding.apply {
                rvMainList.layoutManager = LinearLayoutManager(context)
                adapter = StudentAdapter(studentInfoList)
                rvMainList.adapter = adapter
            }



    }
    // Event 설정
    fun settingEvent(){
        binding.apply {
            btnMainInfo.setOnClickListener {
                // 화면 전환
                parentFragmentManager
                    .beginTransaction() // 트랜잭션 생성
                    .replace(R.id.containerMain, SubFragment())
                    .addToBackStack("SubFragment")
                    .commit() // 실행
            }
            btnMainScore.setOnClickListener {
                // 화면 전환
                parentFragmentManager
                    .beginTransaction()
                    .replace(R.id.containerMain, ThirdFragment())
                    .addToBackStack("ThirdFragment")
                    .commit()
            }
        }

    }

    // 학생 정보 데이터 저장
    fun saveStudentData(name: String, grade: Int) {
        // 학생 정보를 리사이클러 뷰에 추가
        studentInfoList.add(Student(name, grade))
        // 리사이클러 뷰 어댑터에 데이터 변경 알림
        binding.rvMainList.adapter?.notifyItemInserted(studentInfoList.size - 1)
    }


}
