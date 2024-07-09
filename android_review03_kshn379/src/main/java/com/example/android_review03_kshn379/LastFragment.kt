package com.example.android_review03_kshn379

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.android_review03_kshn379.databinding.FragmentLastBinding



class LastFragment : Fragment() {

    private lateinit var binding: FragmentLastBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLastBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 입력한 데이터를 출력하는 코드
        val student = arguments?.getParcelable<Student>("student")
        student?.let {
            binding.studentMainInfo.text = """
                이름: ${it.name}
                학년: ${it.grade}
                
                국어 점수: ${it.kor}
                영어 점수: ${it.eng}
                수학 점수: ${it.math}
                
                총점: ${it.total}
                평균: ${it.average}
            """.trimIndent()
        }

        binding.returnMainFirst.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

    }
}