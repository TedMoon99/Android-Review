package com.example.android_review06_baek08102.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.android_review06_baek08102.R
import com.example.android_review06_baek08102.databinding.FragmentInputBinding


class InputFragment : Fragment() {
    private lateinit var binding:FragmentInputBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_input, container, false)

        return binding.root
    }


}