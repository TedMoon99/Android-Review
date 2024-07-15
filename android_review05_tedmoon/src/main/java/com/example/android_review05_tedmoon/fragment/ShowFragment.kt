package com.example.android_review05_tedmoon.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.android_review05_tedmoon.R
import com.example.android_review05_tedmoon.databinding.FragmentShowBinding
import com.example.android_review05_tedmoon.utils.FragmentName

class ShowFragment : Fragment() {

    private lateinit var binding: FragmentShowBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_show, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Event 설정
        settingEvent()
    }

    // 데이터를 불러와서 화면에 띄워준다
    fun gettingData() {

    }

    // Event 설정
    fun settingEvent() {
        binding.apply {
            toolbarShow.setNavigationOnClickListener {
                // 뒤로가기
                removeFragment()
            }
        }
    }

    // 뒤로가기
    private fun removeFragment() {
        parentFragmentManager.popBackStack(
            FragmentName.SHOW_FRAGMENT.str,
            FragmentManager.POP_BACK_STACK_INCLUSIVE
        )
    }
}