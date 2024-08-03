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
        // toggle button 클릭 시 동작 제어
        setToggleButtonClickEvent()
    }

    // 이벤트 설정
    fun settingEvent() {
        binding.apply {
            // 툴바 이벤트 설정
            inputToolbar.apply {
                setNavigationOnClickListener {
                    removeFragment()
                }
                // 메뉴 아이템 클릭 이벤트 설정
                setOnMenuItemClickListener {

                    Log.d("saveDataProcess", "confirm button clicked!")
                    // 각 동물들의 fragment를 담는 container 비어있지 않을 시
                    // 다시말해 toggle button 클릭으로 fragment가 출력되는 상황에서만
                    if (childFragmentManager.findFragmentById(R.id.input_containter) != null) {

                        // 현재 fragment container에 담겨있는 childFragment id로 가져와서
                        val childFragment = childFragmentManager
                            .findFragmentById(R.id.input_containter) as DataInputListener

                        childFragment.apply {
                            if (isDataValidate()) { // 유효성 검사 통과 시
                                onSaveRequest() // 인터페이스 통하여 childFragment에서 데이터 저장 작업 실행

                                notifySuccess() // 이후 작업 성공 여부 MainFragment로 보내주고

                                removeFragment() // MainFragment로 복귀

                            } else { // 유효성 검사 통과 실패 시
                                // 스낵바 출력
                                showSnackBar("데이터 입력 실패 !!\n입력값을 다시 한 번 확인해 주세요")
                            }
                        }
                    } else { // 현재 fragment container에 아무 것도 담겨있지 않다면
                        // 스낵바 출력
                        showSnackBar("데이터를 입력하고자 하는 동물을 먼저 선택해주세요")
                    }
                    true
                }
            }
        }
    }

    // 버튼 이벤트 설정
    fun setToggleButtonClickEvent() {
        binding.apply {
            // toggle button group 클릭 시
            inputButtonToggleGroup.addOnButtonCheckedListener { group, checkedId, isChecked ->
                if (isChecked) { // 어느 버튼이든 체크가 되었다면
                    changeContainerInstanceById(checkedId)
                }
            }
        }
    }

    // container에 담을 Fragment 변경하는 함수
    fun changeContainerInstanceById(checkedId: Int) { // 매개변수로 선택된 버튼의 id 받아서
        when (checkedId) {
            // 사자 버튼 클릭 시
            R.id.input_buttonToggleGroup_lion -> {
                // 버튼 동작으로 생성된 프래그먼트가 이미 있는지 태그를 통하여 검사 후
                val existingFragment = childFragmentManager.findFragmentByTag("LionFragment")

                // 생성된 프래그먼트가 없다면 = 인스턴스가 없다면
                if (existingFragment == null) {

                    // container에 LionFragment 담음
                    childFragmentManager.beginTransaction()
                        .replace(R.id.input_containter, LionFragment(), "LionFragment")
                        .addToBackStack(ChildFragmentName.Lion_Fragment.name)
                        .commit()

                    Log.d("buttonclick", "Lion Fragment 인스턴스 생성!")

                } else { // 혹은 이미 생성된 인스턴스가 있다면

                    // 앞서 생성 시 사용한 태그를 통하여 해당 인스턴스 검색, 재사용함
                    childFragmentManager.beginTransaction()
                        .replace(R.id.input_containter, existingFragment, "LionFragment")
                        .commit()

                    Log.d("buttonclick", "LionFragment 태그로 기존의 인스턴스 재사용 성공!")
                }
            }

            // 호랑이 버튼 클릭 시
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

            //기린 버튼 클릭 시
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

    // MainFragment에게 데이터 입력 작업 성공 여부 전달
    fun notifySuccess() {
        val bundle = Bundle().apply {
            putBoolean("success", true) // Boolean 타입 번들에 true 값 담아서
        }
        setFragmentResult("Success", bundle) // setFragmentResult로 날림
    }

    // 스낵바 출력 함수
    // 출력하고자 하는 스낵바 메세지 매개변수로 받아서 출력
    fun showSnackBar(message: String) {

        val snackBar = Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT)
        snackBar.setAction("알겠습니다") { }
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

    // 각 동물 Fragment들과 통신하기 위한 interface
    interface DataInputListener {

        // ChildFragment에게 데이터 저장 요청
        fun onSaveRequest()

        // menu item 클릭 시 유효성 검사 결과값 전달해줄 함수
        fun isDataValidate(): Boolean
    }
}