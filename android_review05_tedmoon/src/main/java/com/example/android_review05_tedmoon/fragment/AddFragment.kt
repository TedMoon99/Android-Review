package com.example.android_review05_tedmoon.fragment

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import com.example.android_review05_tedmoon.R
import com.example.android_review05_tedmoon.databinding.DialogAddCustomBinding
import com.example.android_review05_tedmoon.databinding.FragmentAddBinding
import com.example.android_review05_tedmoon.utils.FragmentName
import com.example.android_review05_tedmoon.viewmodel.AddViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar

class AddFragment : Fragment() {

    private lateinit var binding: FragmentAddBinding
    private val viewModel: AddViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add, container, false)
        binding.addViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
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
        binding.apply {
            toolbarAdd.apply {
                // menu를 띄워준다
                inflateMenu(R.menu.menu_add)
            }
        }

    }

    // Event 설정
    fun settingEvent() {
        binding.apply {
            toolbarAdd.apply {
                // navigation
                setNavigationOnClickListener {
                    // 화면을 종료한다
                    removeFragment()
                }
                // menu
                setOnMenuItemClickListener { menu ->
                    when (menu.itemId) {
                        R.id.menuItem_add_complete -> {
                            // 유효성 체크 후

                            // 유효한 입력이면
                            showMessage(true)
                            // 데이터를 저장하고

                            // 화면을 종료한다
//                            removeFragment()
                        }
                    }
                    true
                }
            }
        }
    }

    // 메시지 설정
    fun showMessage(result: Boolean) {
        if (result) { // 모든 항목이 입력되어 있다면
            // 스낵바를 통해 "등록되었습니다"라는 메시지를 보여준다
            showSnackbar()
        } else { // 입력되지 않은 항목이 있다면
            // 다이얼로그로 입력되지 않았다는 메시지를 보여준다
            showDialog()
        }

    }

    // 다이얼로그 만들기
    fun showDialog() {
        // Dialog : 애플리케이션 화면에 메시지를 띄울 때 사용한다
        // 사용자가 버튼을 눌러야지만 없어지는 메시지이다
        val builder = MaterialAlertDialogBuilder(requireContext()).apply {
            // 다이얼로그 타이틀 설정
            setTitle("정보를 입력해주세요")
            // 뷰를 설정
            val dialogAddCustomBinding = DialogAddCustomBinding.inflate(layoutInflater)
            setView(dialogAddCustomBinding.root)

            // Navigation Button을 설정한다
            setPositiveButton("확인") { dialogInterface: DialogInterface, i: Int ->
                // 필요 시 작성
            }
        }
        builder.show()
    }

    // Snackbar 만들기
    fun showSnackbar() {
        // Snackbar : 잠깐 보여줬다가 사라지는 메시지
        // 어플 화면이 떠있을 경우 사용한다
        // Toast와 다르게 지속적으로 띄울 수 있으며 Action을 넣어 이벤트를 설정할 수 있다
        val snackbar = Snackbar.make(requireView(), "등록되었습니다", Snackbar.LENGTH_LONG)
        // 애니메이션 적용
        snackbar.animationMode = Snackbar.ANIMATION_MODE_SLIDE
        // snackbar를 보여준다
        snackbar.show()
    }


    // 뒤로가기
    fun removeFragment() {
        parentFragmentManager.popBackStack(
            FragmentName.ADD_FRAGMENT.str,
            FragmentManager.POP_BACK_STACK_INCLUSIVE
        )
    }
}