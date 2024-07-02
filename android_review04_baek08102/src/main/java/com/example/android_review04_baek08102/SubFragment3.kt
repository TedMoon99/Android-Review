package com.example.android_review04_baek08102

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.android_review04_baek08102.databinding.ActivityMainBinding
import com.example.android_review04_baek08102.databinding.FragmentMainBinding
import com.example.android_review04_baek08102.databinding.FragmentSub3Binding

class SubFragment3 : Fragment() {
    private lateinit var binding: FragmentSub3Binding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSub3Binding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    fun settingView() {

    }

    fun settingEvent() {

    }
}