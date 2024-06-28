package com.example.android_review03_kshn379

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.android_review03_kshn379.databinding.FragmentMainBinding
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
        binding.apply {
            studentMainButton.setOnClickListener {
                val studentMainName = binding.studentMainName.text.toString()
                val studentMainGrade = binding.studentMainGrade.text.toString().toIntOrNull()
                if (studentMainName.isNotEmpty() && studentMainGrade != null) {
                    val mainFragment = parentFragmentManager.findFragmentById(R.id.containerMain) as? MainFragment
                    mainFragment?.saveStudentData(studentMainName, studentMainGrade)
                    parentFragmentManager.popBackStack()
                }
                }

            }


        }
    }


    fun settingEvent() {

    }