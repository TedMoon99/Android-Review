package com.example.android_review04_kshn3792

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android_review04_kshn3792.databinding.FragmentMainBinding
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
    fun settingView() {
        binding.apply {
            // toolbar
            toolBarMain.apply {
                // menu 설정
                inflateMenu(R.menu.menu_main)
            }
            // recyclerView
            recyclerViewMain.apply {
                // deco
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
    fun settingEvent() {
        binding.apply {
            // toolbar
            toolBarMain.apply {
                // navigationIcon 설정

                // menu ClickListener
                setOnMenuItemClickListener { menu ->
                    when(menu.itemId) {
                        R.id.menuItem_main_add -> {
                            // AddFragment 로 이동
                            moveFragment(FragmentName.ADD_FRAGMENT)
                        }
                        R.id.menuItem_main_total -> {
                            // TotalFragment 로 이동

                        }
                    }
                    true
                }
            }
        }
    }
    // 화면 이동
    fun moveFragment(name: FragmentName){
        when(name){
            // AddFragment 로 이동
            FragmentName.ADD_FRAGMENT -> {
                parentFragmentManager
                    .beginTransaction()
                    .replace(R.id.containerMain, AddFragment())
                    .addToBackStack(FragmentName.ADD_FRAGMENT.str)
                    .commit()
            }
            // TotalFragment 로 이동


        }

    }
}