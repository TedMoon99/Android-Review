package com.example.android_review03_baek08102

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android_review03_baek08102.databinding.FragmentMainBinding
import com.google.android.material.divider.MaterialDividerItemDecoration



class MainFragment : Fragment() {

    private lateinit var mainActivity: MainActivity
    private lateinit var binding: FragmentMainBinding
    val dataList: MutableList<StudentData> = mutableListOf()

    private val viewModel: CustomViewModel by activityViewModels()

    // 바인딩 관련
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        mainActivity = activity as MainActivity
        binding = FragmentMainBinding.inflate(inflater)



        return binding.root
    }

    // 일반 함수 호출
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        settingView()
        settingEvent()
    }


    // 뷰 설정
    fun settingView() {
        binding.apply {
            val context = requireContext()
            val deco = MaterialDividerItemDecoration(context, LinearLayoutManager.VERTICAL).apply {
                isLastItemDecorated = false
            }

            mainRecyclerView.apply {
                layoutManager = LinearLayoutManager(context)
                addItemDecoration(deco)
            }
        }
    }

    // 이벤트 설정
    fun settingEvent() {
        binding.apply {

            buttonMainInput.setOnClickListener {

                mainActivity.switchFragment(FragmentName.INPUT_FRAGMENT)
            }

            buttonMainInform.setOnClickListener {

                mainActivity.switchFragment(FragmentName.INFORM_FRAGMENT)
            }
        }
    }
}

