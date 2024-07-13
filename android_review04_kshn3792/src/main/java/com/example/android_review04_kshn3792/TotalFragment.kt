package com.example.android_review04_kshn3792

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.example.android_review04_kshn3792.databinding.FragmentTotalBinding

class TotalFragment : Fragment() {

    private lateinit var binding: FragmentTotalBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTotalBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Event 설정
        settingEvent()
    }
    // Event 설정
    fun settingEvent(){
        binding.apply {
            toolBarTotal.apply {
                // navigationIcon 설정
                setNavigationOnClickListener {
                    // 뒤로 가기
                    parentFragmentManager.popBackStack(FragmentName.TOTAL_FRAGMENT.str, FragmentManager.POP_BACK_STACK_INCLUSIVE)
                }
            }
        }
    }
}