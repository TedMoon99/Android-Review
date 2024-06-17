package com.example.android_review01_tedmoon

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.android_review01_tedmoon.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding

    // 바인딩 괸련된 코드는 onCreateView에 작성해주는 것이 좋고,
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMainBinding.inflate(inflater)
        return binding.root
    }

    // View와 관련된 코드는 onViewCreated에 작성해주는 것이 좋다. - 생명주기(lifecycler)과 관련이 있다
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}