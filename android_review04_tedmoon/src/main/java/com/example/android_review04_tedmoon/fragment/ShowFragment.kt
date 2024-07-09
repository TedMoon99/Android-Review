package com.example.android_review04_tedmoon.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.android_review04_tedmoon.R
import com.example.android_review04_tedmoon.databinding.FragmentShowBinding
import com.example.android_review04_tedmoon.utils.FragmentName
import com.example.android_review04_tedmoon.viewmodel.ShowViewModel
import kotlinx.coroutines.launch

class ShowFragment : Fragment() {
    private lateinit var binding: FragmentShowBinding
    private val viewModel: ShowViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_show, container, false)
        binding.showViewModel = viewModel
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        settingToolbar()
        gettingData()
    }

    // 데이터 초기화
    fun initData(){
        viewModel.initData()
    }

    // 데이터 가져오기
    fun gettingData(){
        val position = arguments?.getInt("position") ?: -1

        viewLifecycleOwner.lifecycle.apply {
            viewModel.gettingData(position + 1)
        }
    }

    // Toolbar
    fun settingToolbar() {
        binding.toolbarShow.apply {
            // 뒤로가기
            setNavigationOnClickListener {
                removeFragment()
            }
        }
    }


    // 뒤로가기
    fun removeFragment() {
        parentFragmentManager.popBackStack(
            FragmentName.SHOW_FRAGMENT.str,
            FragmentManager.POP_BACK_STACK_INCLUSIVE
        )
    }
}