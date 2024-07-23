package com.example.android_review05_kshn379

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import com.example.android_review05_kshn379.databinding.FragmentInfoBinding

class InfoFragment : Fragment() {
    private lateinit var binding: FragmentInfoBinding
    private val viewModel: InfoViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_info, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.infoViewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Event 설정
        settingEvent()
        // Data 초기화
        dataInit()
        // Data 가져오기
        getData()
    }

    private fun settingEvent() {
        binding.apply {
            toolBarInfo.apply {
                // NavigationIcon 설정
                setNavigationOnClickListener {
                    // 뒤로 가기
                    removeFragment()
                }
            }
        }
    }


    // Data 초기화
    fun dataInit() {
        viewModel.dataInit()
    }

    // Data 가져오기
    fun getData() {
        val position = arguments?.getInt("position") ?: -1
        viewLifecycleOwner.lifecycle.apply {
            viewModel.getData(position + 1)
        }
    }

    // 뒤로 가기
    private fun removeFragment() {
        parentFragmentManager.popBackStack(
            FragmentName.INFO_FRAGMENT.str,
            FragmentManager.POP_BACK_STACK_INCLUSIVE
        )
    }
}