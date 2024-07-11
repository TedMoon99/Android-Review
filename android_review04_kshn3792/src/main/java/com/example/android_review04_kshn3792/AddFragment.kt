package com.example.android_review04_kshn3792

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.example.android_review04_kshn3792.databinding.FragmentAddBinding

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
    fun settingView() {
        // toolbar 설정
        binding.apply {
            toolBarAdd.apply {
                inflateMenu(R.menu.menu_add)
            }
        }

    }

    // Event 설정
    fun settingEvent() {
        binding.apply {
            toolBarAdd.apply {
                // navigationIcon 설정
                setNavigationOnClickListener {
                    // 뒤로가기
                    removeFragment()
                }
                // menu
                setOnMenuItemClickListener { menu ->
                    when(menu.itemId){
                        R.id.menuitem_add_save -> {
                            // save

                            // 뒤로가기
                            removeFragment()
                        }
                    }
                    true
                }
            }
        }
    }

    fun removeFragment(){
        parentFragmentManager.popBackStack(FragmentName.ADD_FRAGMENT.str, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }

}