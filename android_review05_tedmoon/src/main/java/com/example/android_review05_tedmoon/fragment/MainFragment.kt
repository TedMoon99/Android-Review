package com.example.android_review05_tedmoon.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android_review05_tedmoon.R
import com.example.android_review05_tedmoon.databinding.FragmentMainBinding
import com.example.android_review05_tedmoon.utils.FragmentName
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
        // View 설정
        settingView()
        // Event 설정
        settingEvent()
    }

    // View 설정
    fun settingView(){
        binding.apply {
            // toolbar
            toolbarMain.apply {
                // menu
                inflateMenu(R.menu.menu_main)
            }
            // recyclerView
            recyclerviewMain.apply {
                // 구분선
                val deco = MaterialDividerItemDecoration(context, LinearLayoutManager.VERTICAL)
                // adapter

                // layoutManager
                layoutManager = LinearLayoutManager(context)
                // 구분선 적용
                addItemDecoration(deco)
            }
        }
    }
    // Event 설정
    fun settingEvent(){
        binding.apply {
            // toolbar
            toolbarMain.apply {
                // menu
                setOnMenuItemClickListener { menu ->
                    when(menu.itemId){
                        R.id.menuItem_main_add -> {
                            // 화면이동

                        }
                    }
                    true
                }
            }
        }
    }
    fun moveFragment(name: FragmentName){
        when(name){
            FragmentName.ADD_FRAGMENT -> {
                // AddFragment 화면으로 이동
            }
        }

    }
}