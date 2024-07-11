package com.example.android_review05_tedmoon.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.example.android_review05_tedmoon.R
import com.example.android_review05_tedmoon.databinding.FragmentAddBinding
import com.example.android_review05_tedmoon.utils.FragmentName

class AddFragment : Fragment() {

    private lateinit var binding: FragmentAddBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // View 설정
        settingView()
        // Event 설정
        settingEvent()
    }

    // View 설정
    fun settingView(){
        binding.apply {
            toolbarAdd.apply {
                // menu를 띄워준다
                inflateMenu(R.menu.menu_add)
            }
        }

    }
    // Event 설정
    fun settingEvent(){
        binding.apply {
            toolbarAdd.apply {
                // navigation
                setNavigationOnClickListener {
                    // 화면을 종료한다
                    removeFragment()
                }
                // menu
                setOnMenuItemClickListener { menu ->
                    when(menu.itemId){
                        R.id.menuItem_add_complete -> {
                            // 유효성 체크 후

                            // 유효한 입력이면

                            // 데이터를 저장하고

                            // 화면을 종료한다
                            removeFragment()
                        }
                    }
                    true
                }
            }
        }
    }

    // 뒤로가기
    fun removeFragment(){
        parentFragmentManager.popBackStack(FragmentName.ADD_FRAGMENT.str, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }
}