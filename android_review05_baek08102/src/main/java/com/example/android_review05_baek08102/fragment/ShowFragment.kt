package com.example.android_review05_baek08102.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.android_review05_baek08102.R
import com.example.android_review05_baek08102.dao.StudentDao
import com.example.android_review05_baek08102.databinding.FragmentShowBinding
import com.example.android_review05_baek08102.model.StudentData
import com.example.android_review05_baek08102.utils.FragmentName
import com.example.android_review05_baek08102.viewmodel.ShowViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ShowFragment : Fragment() {
    private lateinit var binding: FragmentShowBinding
    private val viewModel: ShowViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_show, container, false)
        binding.showViewModel = viewModel // 바인딩에 ShowViewModel 연결
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        settingView()
        settingEvent()
        settingData()
    }

    // 뷰 설정 함수
    fun settingView() {
        binding.apply {
            showToolbar.apply {
                setTitle("학생 정보 보기")
                setNavigationIcon(R.drawable.arrow_back_24px)
            }
        }
    }

    // 이벤트 설정 함수
    fun settingEvent() {
        binding.apply {
            showToolbar.apply {
                setNavigationOnClickListener {
                    removeFragment()
                }
            }
        }
    }

    // 출력 데이터 설정 함수
    fun settingData() {

        viewModel.getData()

        Log.d("showFragment", "getData() 함수 실행 완료")
    }

    // 프래그먼트 삭제 함수
    fun removeFragment() {
        parentFragmentManager.popBackStack(
            FragmentName.Show_Fragment.name,
            FragmentManager.POP_BACK_STACK_INCLUSIVE
        )
    }

}