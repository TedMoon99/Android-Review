package com.example.android_review06_kshn379

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import com.example.android_review06_kshn379.databinding.FragmentAddBinding
import com.google.android.material.snackbar.Snackbar

class AddFragment : Fragment() {

    private lateinit var binding: FragmentAddBinding
//    private val viewModel: AddViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add, container, false)
//        binding.addViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // View 설정
        settingView()
        // Event 설정
        settingEvent()
        // 입력 요소 초기화

    }

    // View 설정
    private fun settingView() {
        // Toolbar 설정
        binding.apply {
            toolBarAdd.apply {
                inflateMenu(R.menu.menu_items)
            }
        }
    }

    // Event 설정
    private fun settingEvent() {
        binding.apply {
            toolBarAdd.apply {
                // navigationIcon 설정
                setNavigationOnClickListener {
                    // snackbar message 출력
                    Snackbar.make(binding.root, "입력하신 정보가 저장되지 않았습니다!!!", Snackbar.LENGTH_LONG)
                        .show()
                    // 뒤로 가기
                    removeFragment()
                }
                // menu 설정
                setOnMenuItemClickListener { menu ->
                    when (menu.itemId) {
                        R.id.menuitems_item_complete -> {
                            // snackbar message 출력
                            Snackbar.make(binding.root, "동물 정보가 등록되었습니다.", Snackbar.LENGTH_SHORT)
                                .show()
                            // 뒤로 가기
                            removeFragment()
                        }
                    }
                    true
                }
            }
        }
    }

    // 뒤로 가기 설정
    private fun removeFragment() {
        parentFragmentManager.popBackStack(
            FragmentName.ADD_FRAGMENT.str,
            FragmentManager.POP_BACK_STACK_INCLUSIVE
        )
    }


}