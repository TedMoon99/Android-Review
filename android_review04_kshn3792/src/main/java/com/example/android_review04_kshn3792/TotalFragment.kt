package com.example.android_review04_kshn3792

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import com.example.android_review04_kshn3792.databinding.FragmentTotalBinding

class TotalFragment : Fragment() {

    private lateinit var binding: FragmentTotalBinding
    private val viewModel: TotalViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_total, container, false)
        binding.totalViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Event 설정
        settingEvent()
        // 데이터 불러오기
        gettingData()
    }

    // Event 설정
    private fun settingEvent() {
        binding.apply {
            toolBarTotal.apply {
                // navigationIcon 설정
                setNavigationOnClickListener {
                    // 뒤로 가기
                    parentFragmentManager.popBackStack(
                        FragmentName.TOTAL_FRAGMENT.str,
                        FragmentManager.POP_BACK_STACK_INCLUSIVE
                    )
                }
            }
        }
    }

    // 데이터 불러오기
    private fun gettingData() {
        viewModel.getData()
    }
}