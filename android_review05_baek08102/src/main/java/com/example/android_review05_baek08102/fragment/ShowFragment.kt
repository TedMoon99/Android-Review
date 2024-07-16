package com.example.android_review05_baek08102.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.android_review05_baek08102.R
import com.example.android_review05_baek08102.databinding.FragmentShowBinding


class ShowFragment : Fragment() {
    private lateinit var binding: FragmentShowBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_show, container, false)
        binding.lifecycleOwner = this

        return binding.root
    }

    fun settingView() {

    }

    fun settingEvent() {

    }

}