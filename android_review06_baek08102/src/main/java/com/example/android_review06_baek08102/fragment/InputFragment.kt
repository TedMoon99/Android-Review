package com.example.android_review06_baek08102.fragment

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.ToggleButton
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.replace
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import com.example.android_review06_baek08102.R
import com.example.android_review06_baek08102.databinding.FragmentInputBinding
import com.example.android_review06_baek08102.model.AnimalData
import com.example.android_review06_baek08102.model.LionData
import com.example.android_review06_baek08102.utils.ChildFragmentName
import com.example.android_review06_baek08102.utils.FragmentName
import com.google.android.material.snackbar.Snackbar


class InputFragment : Fragment() {
    private lateinit var binding: FragmentInputBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_input, container, false)

        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        settingView()
        settingEvent()

    }

    // 뷰 설정
    fun settingView() {
        binding.apply {
            inputToolbar.apply {
                setTitle("동물 등록")
                setNavigationIcon(R.drawable.arrow_back_24px)
                inflateMenu(R.menu.menu_confirm)
            }
        }
    }

    // 이벤트 설정
    fun settingEvent() {
        binding.apply {
            inputToolbar.apply {
                setNavigationOnClickListener {
                    removeFragment()
                }
                setOnMenuItemClickListener {

                    Log.d("saveDataProcess", "confirm button clicked!")

                    if (childFragmentManager.findFragmentById(R.id.input_containter) != null) {

                        val childFragment = childFragmentManager
                            .findFragmentById(R.id.input_containter) as DataInputListener

                        childFragment.apply {
                            if (isDataValidate()) {
                                onSaveRequest()

                                notifySuccess()

                                removeFragment()
                            } else {
                                showSnackBar("데이터 입력 실패 !!\n입력값을 다시 한 번 확인해 주세요")
                            }
                        }
                    } else {
                        showSnackBar("데이터를 입력하고자 하는 동물을 먼저 선택해주세요")
                    }
                    true
                }
            }

            setButtonClickEvent()
        }
    }

    // 버튼 이벤트 설정
    fun setButtonClickEvent() {
        binding.apply {
            inputButtonToggleGroup.addOnButtonCheckedListener { group, checkedId, isChecked ->
                if (isChecked) {
                    changeContainerInstanceById(checkedId)
                }
            }
        }
    }

    fun changeContainerInstanceById(checkedId: Int) {
        when (checkedId) {
            R.id.input_buttonToggleGroup_lion -> {
                val existingFragment = childFragmentManager.findFragmentByTag("LionFragment")
                if (existingFragment == null) {
                    childFragmentManager.beginTransaction()
                        .replace(R.id.input_containter, LionFragment(), "LionFragment")
                        .addToBackStack(ChildFragmentName.Lion_Fragment.name)
                        .commit()
                    Log.d("buttonclick", "Lion Fragment 인스턴스 생성!")
                } else {
                    childFragmentManager.beginTransaction()
                        .replace(R.id.input_containter, existingFragment, "LionFragment")
                        .commit()
                    Log.d("buttonclick", "LionFragment 태그로 기존의 인스턴스 재사용 성공!")
                }
            }

            R.id.input_buttonToggleGroup_tiger -> {
                val existingFragment = childFragmentManager.findFragmentByTag("TigerFragment")
                if (existingFragment == null) {
                    childFragmentManager.beginTransaction()
                        .replace(R.id.input_containter, TigerFragment(), "TigerFragment")
                        .addToBackStack(ChildFragmentName.Tiger_Fragment.name)
                        .commit()
                    Log.d("buttonclick", "Tiger Fragment 인스턴스 생성!")
                } else {

                    childFragmentManager.beginTransaction()
                        .replace(R.id.input_containter, existingFragment, "TigerFragment")
                        .commit()
                    Log.d("buttonclick", "TigerFragment 태그로 기존의 인스턴스 재사용 성공!")
                }
            }

            R.id.input_buttonToggleGroup_giraffe -> {
                val existingFragment =
                    childFragmentManager.findFragmentByTag("GiraffeFragment")
                if (existingFragment == null) {
                    childFragmentManager.beginTransaction()
                        .replace(R.id.input_containter, GiraffeFragment(), "GiraffeFragment")
                        .addToBackStack(ChildFragmentName.Giraffe_Fragment.name)
                        .commit()
                    Log.d("buttonclick", "Giraffe Fragment 인스턴스 생성!")
                } else {
                    childFragmentManager.beginTransaction()
                        .replace(R.id.input_containter, existingFragment, "GiraffeFragment")
                        .commit()
                    Log.d("buttonclick", "GiraffeFragment 태그로 기존의 인스턴스 재사용 성공!")
                }
            }
        }
    }

    fun notifySuccess() {
        val bundle=Bundle().apply {
            putBoolean("success",true)
        }
        setFragmentResult("Success",bundle)
    }

    // 스낵바 출력 함수
    fun showSnackBar(message: String) {
        val snackBar = Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT)
        snackBar.setAction("알겠습니다"){ }
        snackBar.animationMode = Snackbar.ANIMATION_MODE_FADE
        snackBar.show()
    }

    // 현재 프래그먼트 제거
    fun removeFragment() {
        parentFragmentManager.popBackStack(
            FragmentName.Input_Fragment.name,
            FragmentManager.POP_BACK_STACK_INCLUSIVE
        )
    }

    interface DataInputListener {
        fun onSaveRequest()
        fun isDataValidate(): Boolean
    }


}