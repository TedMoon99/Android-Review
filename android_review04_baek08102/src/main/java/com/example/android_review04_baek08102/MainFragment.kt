package com.example.android_review04_baek08102

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android_review04_baek08102.databinding.ActivityMainBinding
import com.example.android_review04_baek08102.databinding.FragmentMainBinding
import com.google.android.material.divider.MaterialDividerItemDecoration

class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMainBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        settingView()
        settingEvent()
    }

    fun settingView() {
        binding.apply {

            // recyclerView 관련 작업
            val context = requireContext()
            val deco = MaterialDividerItemDecoration(context, LinearLayoutManager.VERTICAL).apply {
                isLastItemDecorated = false
            }
            mainRecyclerView.apply {
                layoutManager = LinearLayoutManager(context)
                addItemDecoration(deco)
            }

            // toolbar 관련 작업
            mainToolBar.apply {
                setTitle("당신의 점수는?")
                setNavigationIcon(R.drawable.photo_camera_24px)
                inflateMenu(R.menu.menu_main)
            }
        }
    }

    fun settingEvent() {
        binding.apply {
            // toolbar 관련 event 설정
            mainToolBar.apply {
                // navigation icon 클릭시
                setNavigationOnClickListener {
                    val toast = Toast.makeText(context, "찰칵!", Toast.LENGTH_SHORT)
                    toast.show()
                }
                setOnMenuItemClickListener { item ->
                    when (item.itemId) {
                        R.id.menu_main_input -> {
                            parentFragmentManager
                                .beginTransaction()
                                .replace(R.id.main_container, SubFragment1())
                                .addToBackStack(FragmentName.SUB_FRAGMENT1.name)
                                .commit()
                        }

                        R.id.menu_main_total -> {
                            parentFragmentManager
                                .beginTransaction()
                                .replace(R.id.main_container, SubFragment2())
                                .addToBackStack(FragmentName.SUB_FRAGMENT2.name)
                                .commit()
                        }
                    }
                    true
                }
            }
        }
    }
}
