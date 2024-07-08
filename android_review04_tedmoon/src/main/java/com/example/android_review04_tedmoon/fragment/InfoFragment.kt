package com.example.android_review04_tedmoon.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import com.example.android_review04_tedmoon.R
import com.example.android_review04_tedmoon.databinding.FragmentInfoBinding
import com.example.android_review04_tedmoon.utils.FragmentName
import com.example.android_review04_tedmoon.viewmodel.InfoViewModel

class InfoFragment : Fragment() {

    private lateinit var binding: FragmentInfoBinding
    val viewModel: InfoViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_info, container, false)
        binding.infoViewModel = viewModel
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        settingToolbar()
        settingEvent()
        gettingData()
    }

    // 데이터 받아오기
    fun gettingData() {
        viewModel.getData()
    }


    // Toolbar 설정
    fun settingToolbar() {
        binding.toolbarInfo.apply {
            // menu
            inflateMenu(R.menu.menu_info)
        }
    }

    // Evenet 설정
    fun settingEvent() {
        binding.apply {
            toolbarInfo.apply {

                // 뒤로가기
                setNavigationOnClickListener {
                    removeFragment()
                }

                // menu 클릭 시
                setOnMenuItemClickListener {
                    when (it.itemId){
                        R.id.menuItem_info_complete -> {
                            // 뒤로가기
                            removeFragment()
                        }
                    }
                    true
                }
            }
        }

    }

    fun removeFragment() {
        parentFragmentManager.popBackStack(
            FragmentName.INFO_FRAGMENT.str,
            FragmentManager.POP_BACK_STACK_INCLUSIVE
        )
    }
}