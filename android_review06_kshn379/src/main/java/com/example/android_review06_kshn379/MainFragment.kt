package com.example.android_review06_kshn379

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android_review06_kshn379.databinding.CustomDialogBinding
import com.example.android_review06_kshn379.databinding.FragmentMainBinding
import com.google.android.material.divider.MaterialDividerItemDecoration

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
//    private val animalList: ArrayList<ZooInfo> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
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

    override fun onResume() {
        super.onResume()
        // Fragment 화면 전환 시 마다 데이터 갱신

    }

    // View 설정
    private fun settingView() {
        binding.apply {
            // toolbar 설정
            toolBarMain.apply {
                // menu 설정
                inflateMenu(R.menu.menu_filter)
            }
            // RecyclerView 설정
            recyclerViewMain.apply {
                // 구분선 설정
                val deco = MaterialDividerItemDecoration(context, LinearLayoutManager.VERTICAL)
                // Adapter 연결

                // LayoutManager 적용

                // 구분선 추가
                addItemDecoration(deco)
            }
        }
    }

    // Event 설정
    private fun settingEvent() {
        binding.apply {
            // Floating Action Button 설정
            faButtonMain.apply {
                setOnClickListener { fab ->
                    when (fab.id) {
                        R.id.faButton_main -> {
                            // AddFragment 화면 이동
                            moveFragment(FragmentName.ADD_FRAGMENT)
                        }
                    }
                }
            }
            // Toolbar 설정
            toolBarMain.apply {
                // menu 클릭 설정
                setOnMenuItemClickListener { menu ->
                    when (menu.itemId) {
                        R.id.menufilter_item_select -> {
                            // 클릭 시 Dialog 화면 구현
                            // custom_dialog.xml 파일을 inflate 한다
                            val mDialogView: CustomDialogBinding =
                                DataBindingUtil.inflate(
                                    LayoutInflater.from(context),
                                    R.layout.custom_dialog,
                                    null,
                                    false
                                )
                            // AlertDialog 설정
                            val mBuilder = AlertDialog.Builder(context)
                                .setView(mDialogView.root)
                            // AlertDialog 객체 생성 및 화면 표시
                            val mAlertDialog = mBuilder.show()
                            // Dialog 외부 클릭 시 화면 닫히지 않게 설정
                            mAlertDialog.setCancelable(false)
                            mAlertDialog.setCanceledOnTouchOutside(false)

                            // 확인 버튼 클릭 시 데이터 전달 후 다이얼로그 닫기
                            mDialogView.buttonSelectConfirm.setOnClickListener {
                                mAlertDialog.dismiss()
                            }

                            // 취소 버튼 클릭 시 다이얼로그 닫기
                            mDialogView.buttonSelectCancel.setOnClickListener {
                                mAlertDialog.dismiss()
                            }
                            // Dialog의 CheckedTextView 설정
                            checkedDialog(mDialogView)
                        }
                    }
                    true
                }
            }
        }
    }

    // 화면 이동
    private fun moveFragment(name: FragmentName) {
        when (name) {
            // AddFragment 로 이동
            FragmentName.ADD_FRAGMENT -> {
                parentFragmentManager
                    .beginTransaction()
                    .replace(R.id.containerMain, AddFragment())
                    .addToBackStack(name.str)
                    .commit()
            }
        }
    }

    // Dialog CheckedTextView 설정
    private fun checkedDialog(dialogBinding: CustomDialogBinding) {
        dialogBinding.apply {
            // 초기 전체 선택 설정
            checkedTextViewAll.isChecked = true
            checkedTextViewLion.isChecked = true
            checkedTextViewTiger.isChecked = true
            checkedTextViewGiraffe.isChecked = true
            // '전체' 클릭 시 상태 설정
            checkedTextViewAll.setOnClickListener {
                if (checkedTextViewAll.isChecked) {
                    checkedTextViewAll.isChecked = false
                    checkedTextViewLion.isChecked = false
                    checkedTextViewTiger.isChecked = false
                    checkedTextViewGiraffe.isChecked = false
                } else {
                    checkedTextViewAll.isChecked = true
                    checkedTextViewLion.isChecked = true
                    checkedTextViewTiger.isChecked = true
                    checkedTextViewGiraffe.isChecked = true
                }
            }
            checkedTextViewLion.apply {
                setOnClickListener {
                    if (checkedTextViewLion.isChecked) {
                        checkedTextViewAll.isChecked = false
                        checkedTextViewLion.isChecked = false
                    } else if (!checkedTextViewLion.isChecked && checkedTextViewTiger.isChecked && checkedTextViewGiraffe.isChecked) {
                        checkedTextViewAll.isChecked = true
                        checkedTextViewLion.isChecked = true
                    } else {
                        checkedTextViewLion.isChecked = true
                    }
                    checkedTextViewTiger.apply {
                        setOnClickListener {
                            if (checkedTextViewTiger.isChecked) {
                                checkedTextViewAll.isChecked = false
                                checkedTextViewTiger.isChecked = false
                            } else if (checkedTextViewLion.isChecked && !checkedTextViewTiger.isChecked && checkedTextViewGiraffe.isChecked) {
                                checkedTextViewAll.isChecked = true
                                checkedTextViewTiger.isChecked = true
                            } else {
                                checkedTextViewTiger.isChecked = true
                            }
                        }
                        checkedTextViewGiraffe.apply {
                            setOnClickListener {
                                if (checkedTextViewGiraffe.isChecked) {
                                    checkedTextViewAll.isChecked = false
                                    checkedTextViewGiraffe.isChecked = false
                                } else if (checkedTextViewLion.isChecked && checkedTextViewTiger.isChecked && !checkedTextViewGiraffe.isChecked) {
                                    checkedTextViewAll.isChecked = true
                                    checkedTextViewGiraffe.isChecked = true
                                } else {
                                    checkedTextViewGiraffe.isChecked = true
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}