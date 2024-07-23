package com.example.android_review05_kshn379

import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android_review05_kshn379.databinding.FragmentMainBinding
import com.google.android.material.divider.MaterialDividerItemDecoration
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
    private val dataList: ArrayList<ManageInfo> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
        registerForContextMenu(binding.recyclerViewMain)
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
        // Fragment가 화면에 다시 전환될 때마다 데이터 갱신
        getAdapterData()
    }

    // View 설정
    private fun settingView() {
        binding.apply {
            // Toolbar
            toolBarMain.apply {
                // menu 설정
                inflateMenu(R.menu.menu_add)
            }
            // RecyclerView 설정
            recyclerViewMain.apply {
                // deco 설정
                val deco = MaterialDividerItemDecoration(context, LinearLayoutManager.VERTICAL)
                // Adapter 연결
                adapter = StudentAdapter(dataList, parentFragmentManager)
                // LayoutManager 적용
                layoutManager = LinearLayoutManager(context)
                // 구분선 적용
                addItemDecoration(deco)
            }
        }
    }

    private fun getAdapterData() {
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                // 백그라운드 스레드에서 데이터 가져오기
                val data = withContext(Dispatchers.IO) { AddDao.getAllData() }

                dataList.clear()
                dataList.addAll(data)
                // studentIdx로 정렬
                dataList.sortBy { it.studentIdx }
                // Adapter 변경 알림
                binding.recyclerViewMain.adapter?.notifyDataSetChanged()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Event 설정
    private fun settingEvent() {
        binding.apply {
            // Toolbar
            toolBarMain.apply {
                // navigationIcon 설정
                setNavigationOnClickListener {
                    Toast.makeText(context, "Data Store", Toast.LENGTH_SHORT).show()
                }
                // menu Click 설정
                setOnMenuItemClickListener { menu ->
                    when (menu.itemId) {
                        R.id.menuitem_add_append -> {
                            // AddFragment 로 화면 이동
                            moveFragment(FragmentName.ADD_FRAGMENT)
                        }
                    }
                    true
                }
            }
        }
    }

    // 화면 이동
    private fun moveFragment(name: FragmentName) {
        when (name) {
            // AddFragment로 이동
            FragmentName.ADD_FRAGMENT -> {
                parentFragmentManager
                    .beginTransaction()
                    .replace(R.id.containerMain, AddFragment())
                    .addToBackStack(name.str)
                    .commit()
            }

            else -> Log.d("nothing", "Page 404 Error")
        }
    }

}