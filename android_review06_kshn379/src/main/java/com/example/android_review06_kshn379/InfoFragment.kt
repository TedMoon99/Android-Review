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
import com.google.android.material.snackbar.Snackbar


// InfoFragment Index send(mainFragment position value) -> To EditFragment Index
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
        // Data 초기화
        clearText()
        // Data 가져오기
        getData()

    }

    // Data 갱신
    override fun onResume() {
        super.onResume()
        getData()
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
                            // RecyclerView Item 삭제 설정
                            // position 값을 arguments에서 가져오고, 기본값은 -1
                            val position = arguments?.getInt("position") ?: -1
                            // position 값이 유효한지 확인 -> 0 이상일 경우 유효
                            if (position != -1) {
                                // position + 1 값을 removeItem에 전달하여 삭제 요청
                                // position은 0부터 시작하는 Index이므로 1을 더한다
                                viewModel.removeItem(position)
                            }

                            // 뒤로 가기
                            removeFragment()
                            // SnackBar Message 출력
                            Snackbar.make(binding.root, "해당 정보가 삭제 되었습니다!!!", Snackbar.LENGTH_SHORT)
                                .show()
                            true
                        }

                        else -> false
                    }
                }
            }
        }
    }

    // Data 초기화
    private fun clearText() {
        viewModel.clearText()
    }

    // Data 가져오기
    private fun getData() {
        val position = arguments?.getInt("position") ?: -1
        viewLifecycleOwner.lifecycle.apply {
            viewModel.getData(position)
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
