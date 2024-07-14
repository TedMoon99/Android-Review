package com.example.android_review04_kshn3792

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android_review04_kshn3792.databinding.FragmentMainBinding
import com.google.android.material.divider.MaterialDividerItemDecoration
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private val dataList: ArrayList<StudentInfo> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // View 설정
        settingView()
        // Event 설정
        settingEvent()
    }

    override fun onResume() {
        super.onResume()
        // Fragment가 다시 화면에 나타날 때마다 데이터 갱신
        gettingAdapterData()
        Log.d("onResume", "Activate")
    }

    // View 설정
    private fun settingView() {
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
                // adapter 연결
                adapter = StudentAdpater(dataList, parentFragmentManager)
                // layoutManager 적용
                layoutManager = LinearLayoutManager(context)
                // 구분선 적용
                addItemDecoration(deco)
            }
        }
    }

    private fun gettingAdapterData() {
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                // 백그라운드 스레드에서 데이터를 가져온다
                val data = withContext(Dispatchers.IO){AddDao.getAllData()}

                dataList.clear()
                dataList.addAll(data)
                // studentIdx로 정렬
                dataList.sortBy { it.studentIdx }
                // 어댑터 변경 알림
                binding.recyclerViewMain.adapter?.notifyDataSetChanged()
            } catch (e: Exception){
                e.printStackTrace()
            }
        }
    }

    // Event 설정
    private fun settingEvent() {
        binding.apply {
            // toolbar
            toolBarMain.apply {
                // navigationIcon 설정
                setNavigationOnClickListener {
                    Toast.makeText(context, "데이터", Toast.LENGTH_SHORT).show()
                }
                // menu ClickListener
                setOnMenuItemClickListener { menu ->
                    when(menu.itemId) {
                        R.id.menuItem_main_add -> {
                            // AddFragment 로 이동
                            moveFragment(FragmentName.ADD_FRAGMENT)
                        }
                        R.id.menuItem_main_total -> {
                            // TotalFragment 로 이동
                            moveFragment(FragmentName.TOTAL_FRAGMENT)
                        }
                    }
                    true
                }
            }
        }
    }
    // 화면 이동
    private fun moveFragment(name: FragmentName){
        when(name){
            // AddFragment 로 이동
            FragmentName.ADD_FRAGMENT -> {
                parentFragmentManager
                    .beginTransaction()
                    .replace(R.id.containerMain, AddFragment())
                    .addToBackStack(name.str)
                    .commit()
            }
            // TotalFragment 로 이동
            FragmentName.TOTAL_FRAGMENT -> {
                parentFragmentManager
                    .beginTransaction()
                    .replace(R.id.containerMain, TotalFragment())
                    .addToBackStack(name.str)
                    .commit()
            }

            else -> Log.d("test", "Wrong Page")
        }
    }
}