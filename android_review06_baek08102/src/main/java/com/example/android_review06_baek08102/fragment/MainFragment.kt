package com.example.android_review06_baek08102.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.android_review06_baek08102.R
import com.example.android_review06_baek08102.databinding.DialogMainBinding
import com.example.android_review06_baek08102.databinding.FragmentMainBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        settingView()
        settingEvent()
    }

    // 뷰 설정
    fun settingView() {
        binding.apply {
            mainToolbar.apply {
                setTitle("동물원 관리")
                inflateMenu(R.menu.menu_main)
            }

            mainRecyclerView.apply {
                // adapter 연결
                // deco 추가
            }
        }

    }

    // 이벤트 설정
    fun settingEvent() {
        binding.apply {
            mainToolbar.apply {
                setOnMenuItemClickListener { item ->
                    when (item.itemId) {
                        R.id.menu_item_filter -> {
                            showDialog()
                        }
                    }

                    true
                }
            }
        }
        // 메뉴 클릭 이벤트

        // 다이얼로그 띄우기
    }

    // 다이얼로그 출력
    fun showDialog() {
        val builder = MaterialAlertDialogBuilder(requireContext())
        val dialogBinding = DialogMainBinding.inflate(layoutInflater)

        builder.apply {

            setView(dialogBinding.root)
            settingCheckedState(dialogBinding)

            setPositiveButton("확인") { dialog, id ->
                // 확인 버튼 클릭 시
                // 체크박스 조건에 따라 리사이클러뷰 갱신 필요
                // 해당 작업 함수 만든 뒤 호출하면 될 듯

                settingViewByChecked(dialogBinding)
            }
            setNegativeButton("취소") { dialog, id ->
                // 취소 버튼 클릭 시 다이얼로그만 종료되면 됨
            }
            builder.show()
        }
    }

    // 다이얼로그 체크박스 체크 조건으로 출력 조정
    fun settingCheckedState(binding: DialogMainBinding) {

        binding.apply {

            // 체크박스 조건 설정
            // All 체크박스의 체크 조건
            val updateAllCheckBoxState: () -> Unit = {
                // lion, tiger, giraffe 가 모두 체크된다면
                val allChecked = dialogCheckBoxLion.isChecked &&
                        dialogCheckBoxTiger.isChecked &&
                        dialogCheckBoxGiraffe.isChecked

                // all 박스 체크, 그렇지 않다면 체크 해제
                dialogCheckBoxAll.isChecked = allChecked
            }

            // 체크박스 리스너
            // All 체크 시 모두 체크
            dialogCheckBoxAll.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    dialogCheckBoxLion.isChecked = true
                    dialogCheckBoxTiger.isChecked = true
                    dialogCheckBoxGiraffe.isChecked = true
                }
            }

            // lion, tiger, giraffe 체크 혹은 해제 시 all 체크 조건 검사
            dialogCheckBoxLion.setOnCheckedChangeListener { _, _ -> updateAllCheckBoxState() }
            dialogCheckBoxTiger.setOnCheckedChangeListener { _, _ -> updateAllCheckBoxState() }
            dialogCheckBoxGiraffe.setOnCheckedChangeListener { _, _ -> updateAllCheckBoxState() }
        }
    }

    fun settingViewByChecked(binding: DialogMainBinding) {

    }
}