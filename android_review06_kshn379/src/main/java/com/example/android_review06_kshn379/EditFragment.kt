package com.example.android_review06_kshn379

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import com.example.android_review06_kshn379.databinding.FragmentEditBinding
import com.google.android.material.snackbar.Snackbar

class EditFragment : Fragment() {

    private lateinit var binding: FragmentEditBinding
    private val viewModel: AddViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_edit, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.addViewModel = viewModel
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
    private fun settingView() {
        binding.apply {
            // toolbar 설정
            toolBarEdit.apply {
                inflateMenu(R.menu.menu_rename)
            }
        }
    }

    // Event 설정
    private fun settingEvent() {
        binding.apply {
            // toolbar 설정
            toolBarEdit.apply {
                // navigationIcon 설정
                setNavigationOnClickListener {
                    // SnackBar Message 출력
                    Snackbar.make(binding.root, "정보가 수정되지 않았습니다!", Snackbar.LENGTH_SHORT).show()
                    // 뒤로 가기
                    removeFragment()
                }
                // menu 설정
                setOnMenuItemClickListener { rename ->
                    when (rename.itemId) {
                        R.id.menuitem_rename_edit -> {
                            // SnackBar Message 출력
                            Snackbar.make(binding.root, "동물 정보가 수정되었습니다", Snackbar.LENGTH_SHORT)
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
            FragmentName.INFO_FRAGMENT.str,
            FragmentManager.POP_BACK_STACK_INCLUSIVE
        )
    }
}