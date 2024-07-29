package com.example.android_review06_kshn379

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import com.example.android_review06_kshn379.databinding.FragmentInfoBinding

class InfoFragment : Fragment() {
    private lateinit var binding: FragmentInfoBinding
    private val viewModel: AddViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_info, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.addViewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Event 설정
        settingEvent()
        // View 설정
        settingView()

    }

    // View 설정
    private fun settingView() {
        binding.apply {
            // toolbar 설정
            toolBarInfo.apply {
                inflateMenu(R.menu.menu_edit)
            }
        }
    }

    // Event 설정
    private fun settingEvent() {
        binding.apply {
            toolBarInfo.apply {
                // navigation icon 설정
                setNavigationOnClickListener {
                    // 뒤로 가기
                    removeFragment()
                }

                setOnMenuItemClickListener { item ->
                    when (item.itemId) {
                        // menu edit icon 설정
                        R.id.menuitem_edit_item -> {
                            // EditFragment 화면 이동
                            moveFragment(FragmentName.EDIT_FRAGMENT)
                            true
                        }
                        // menu delete icon 설정
                        R.id.menuitem_edit_delete -> {
                            // 뒤로 가기
                            removeFragment()
                            true
                        }

                        else -> false
                    }
                }
            }
        }
    }


    // menu Edit Icon 설정
    private fun moveFragment(name: FragmentName) {
        when (name) {
            // EditFragment 화면 이동
            FragmentName.EDIT_FRAGMENT -> {
                parentFragmentManager
                    .beginTransaction()
                    .replace(R.id.containerMain, EditFragment())
                    .addToBackStack(name.str)
                    .commit()
            }

            else -> Log.d("Wrong page", "Page 404 Error")

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
