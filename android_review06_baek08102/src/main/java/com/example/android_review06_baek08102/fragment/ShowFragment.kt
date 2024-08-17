package com.example.android_review06_baek08102.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import com.example.android_review06_baek08102.R
import com.example.android_review06_baek08102.databinding.FragmentShowBinding
import com.example.android_review06_baek08102.utils.FragmentName
import com.example.android_review06_baek08102.viewmodel.ShowViewModel


class ShowFragment : Fragment() {
    private lateinit var binding: FragmentShowBinding
    private val viewModel: ShowViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_show, container, false)
        binding.showViewModel = viewModel
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        updataData()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        settingData()
        settingView()
        settingEvent()
    }

    fun settingView() {
        binding.apply {
            showToolbar.apply {
                setTitle("동물 정보")
                setNavigationIcon(R.drawable.arrow_back_24px)
                inflateMenu(R.menu.menu_show)
            }
        }
    }

    fun settingEvent() {
        binding.apply {
            showToolbar.apply {
                setNavigationOnClickListener {
                    removeFragment()
                }

                setOnMenuItemClickListener { item ->
                    when (item.itemId) {
                        R.id.menu_item_edit -> {

                            // animalType에 따른 화면 출력 작업

                            parentFragmentManager
                                .beginTransaction()
                                .replace(R.id.main_container, EditFragment())
                                .addToBackStack(FragmentName.Edit_Fragment.name)
                                .commit()
                        }

                        R.id.menu_item_delete -> {

                            // 현재 화면에 출력되는 데이터 삭제 (dataState false로 변경)
                            // 작업 예정

                            // 이후 MainFragment로 복귀
                            parentFragmentManager
                                .popBackStack(
                                    FragmentName.Show_Fragment.name,
                                    FragmentManager.POP_BACK_STACK_INCLUSIVE
                                )
                        }
                    }
                    true
                }
            }
        }
    }

    // 화면에 출력할 데이터 세팅 함수
    fun settingData() {
        // recycler item 클릭 시 전달받은 position 번째 datalist의 animalIdx value
        val index = viewModel.clickedIdx.value

        Log.d("initShowData", "settingData has called")

        // 전달받은 index 데이터가 null이 아닐 시,
        // 다시말해 제대로 전달이 이루어 졌을 시에만
        if (index != null) {

            viewModel.initShowDataByAnimalType()

        }
    }

    // 화면 출력할 데이터 업데이트 하는 함수
    fun updataData() {
        // 뷰모델에서 출력 데이터 초기화 작업 완료 시
        // showDataFetchStatus의 값 1로 변경
        viewModel.showDataFetchStatus.observe(viewLifecycleOwner) { status ->
            when (status) {
                // 이곳에서 fragment 출력 데이터 설정 후
                // showDataFetchStatus의 값 0으로 변경
                // updataData 함수의 반복 출력 방지
                1 -> {
                    viewModel.settingDataByAnimalType()
                }
            }
        }
    }

    // 현재 프래그먼트 popBackStack
    fun removeFragment() {
        parentFragmentManager.popBackStack(
            FragmentName.Show_Fragment.name,
            FragmentManager.POP_BACK_STACK_INCLUSIVE
        )
    }
}