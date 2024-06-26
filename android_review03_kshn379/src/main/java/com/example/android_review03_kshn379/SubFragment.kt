package com.example.android_review03_kshn379

import android.os.Bundle
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.android_review03_kshn379.databinding.FragmentSubBinding

class SubFragment : Fragment() {

    lateinit var binding: FragmentSubBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSubBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        settingView()

        settingEvent()

    }

    fun settingView() {


    }


    fun settingEvent() {
        binding.apply {
            studentMainButton.setOnClickListener {

                // 프래그먼트를 종료한다
                removeFragment()
            }
        }
    }

    fun removeFragment(){
        SystemClock.sleep(200)
        parentFragmentManager.popBackStack("SubFragment", FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }
}