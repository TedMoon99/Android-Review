package com.example.android_review05_tedmoon.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android_review05_tedmoon.R
import com.example.android_review05_tedmoon.adapter.CustomAdapter
import com.example.android_review05_tedmoon.databinding.FragmentMainBinding
import com.example.android_review05_tedmoon.model.ScoreInfo
import com.example.android_review05_tedmoon.utils.FragmentName
import com.example.android_review05_tedmoon.viewmodel.MainViewModel
import com.example.android_review05_tedmoon.viewmodel.ShowViewModel
import com.google.android.material.divider.MaterialDividerItemDecoration
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private val viewModel: MainViewModel by activityViewModels()
    private val showViewModel: ShowViewModel by activityViewModels()
    var dataList: ArrayList<ScoreInfo> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        binding.mainViewModel = viewModel
        binding.lifecycleOwner = this
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
        gettingData()
    }

    // View 설정
    fun settingView() {
        binding.apply {
            // toolbar
            toolbarMain.apply {
                // menu
                inflateMenu(R.menu.menu_main)
            }
            // recyclerView
            recyclerviewMain.apply {
                // 구분선
                val deco =
                    MaterialDividerItemDecoration(context, LinearLayoutManager.VERTICAL).apply {
                        // 마지막 구분선 제거
                        isLastItemDecorated = false
                    }
                // menuInflater 받아오기
                val menuInflater = requireActivity().menuInflater
                // FragmentManager 받아오기
                val manager = parentFragmentManager
                // adapter
                adapter = CustomAdapter(dataList, menuInflater, manager, viewModel, showViewModel)
                // layoutManager
                layoutManager = LinearLayoutManager(context)
                // 구분선 적용
                addItemDecoration(deco)
            }
        }
    }
    // DB에서 정보 불러오기
    fun gettingData(){
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val data = withContext(Dispatchers.IO) { viewModel.getAllData() }
                dataList.clear()
                dataList.addAll(data)
                binding.recyclerviewMain.adapter?.notifyDataSetChanged()
                Log.d("MainFragment", "데이터 조회 성공 : ${dataList}")
            } catch (e: Exception){
                Log.e("MainFragment", "데이터 조회 실패 : ${e.message}")
            }
        }
    }

    // Event 설정
    fun settingEvent() {
        binding.apply {
            // toolbar
            toolbarMain.apply {
                // menu
                setOnMenuItemClickListener { menu ->
                    when (menu.itemId) {
                        R.id.menuItem_main_add -> {
                            // 화면이동
                            moveFragment(FragmentName.ADD_FRAGMENT)
                        }
                    }
                    true
                }
            }
        }
    }

    fun moveFragment(name: FragmentName) {
        when (name) {
            // 입력 추가 화면
            FragmentName.ADD_FRAGMENT -> {
                // AddFragment 화면으로 이동
                parentFragmentManager.beginTransaction().replace(R.id.containerMain, AddFragment())
                    .addToBackStack(FragmentName.ADD_FRAGMENT.str).commit()
            }

            else -> {
                Log.d("MainFragment", "잘못된 입력")
            }
        }

    }
}