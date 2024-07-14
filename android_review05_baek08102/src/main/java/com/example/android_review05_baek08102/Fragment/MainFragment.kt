package com.example.android_review05_baek08102.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android_review05_baek08102.Utils.FragmentName
import com.example.android_review05_baek08102.R
import com.example.android_review05_baek08102.databinding.FragmentMainBinding
import com.google.android.material.divider.MaterialDividerItemDecoration

class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)

        binding.lifecycleOwner = this
        return binding.root
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
                                .addToBackStack(FragmentName.Input_Fragment.name).commit()
                        }
                    }
                    true
                }
            }
        }
    }
}