package com.example.android_review05_baek08102.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android_review05_baek08102.utils.FragmentName
import com.example.android_review05_baek08102.R
import com.example.android_review05_baek08102.adapter.CustomAdapter
import com.example.android_review05_baek08102.dao.StudentDao
import com.example.android_review05_baek08102.databinding.FragmentMainBinding
import com.example.android_review05_baek08102.model.StudentData
import com.example.android_review05_baek08102.viewmodel.ShowViewModel
import com.google.android.material.divider.MaterialDividerItemDecoration
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
    private val dataList: ArrayList<StudentData> = arrayListOf()
    private val showViewModel: ShowViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)

        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onResume() {
        super.onResume()

        settingData()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        settingView()
        settingEvent()

    }

    fun settingView() {
        binding.apply {
            mainToolbar.apply {
                setTitle("학생 정보 관리")
                inflateMenu(R.menu.menu_main)
            }
            val context = requireContext()
            val deco = MaterialDividerItemDecoration(context, LinearLayoutManager.VERTICAL).apply {
                isLastItemDecorated = false
            }
            mainRecyclerView.apply {

                val menuInflater = requireActivity().menuInflater

                adapter = CustomAdapter(dataList, parentFragmentManager, menuInflater, showViewModel)
                adapter?.notifyDataSetChanged()

                layoutManager = LinearLayoutManager(context)
                addItemDecoration(deco)
            }
        }
    }

    fun settingEvent() {
        binding.apply {
            mainToolbar.apply {
                setOnMenuItemClickListener { item ->
                    when (item.itemId) {
                        R.id.menu_item_add -> {
                            parentFragmentManager
                                .beginTransaction()
                                .replace(R.id.main_containerView, InputFragment())
                                .addToBackStack(FragmentName.Input_Fragment.name)
                                .commit()
                        }
                    }
                    true
                }
            }
        }
    }

    fun settingData() {
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val data = withContext(Dispatchers.IO) { StudentDao.getAllData() }

                dataList.clear()
                dataList.addAll(data)
                dataList.sortBy { it.studentIdx }
                binding.mainRecyclerView.adapter?.notifyDataSetChanged()

            } catch (e: Exception) {
                Log.e("MainFragment", "settingData failed : ${e.message}")
            }
        }
    }
}