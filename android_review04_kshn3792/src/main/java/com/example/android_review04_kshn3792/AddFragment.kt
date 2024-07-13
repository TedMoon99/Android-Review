package com.example.android_review04_kshn3792

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.android_review04_kshn3792.databinding.FragmentAddBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddFragment : Fragment() {

    private lateinit var binding: FragmentAddBinding
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddBinding.inflate(inflater)
        binding.addViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    // 유효성 검사
    fun validateInput(): Boolean {
        // 입력 요소를 가져온다
        val name = viewModel.studentName.value ?: ""
        val grade = viewModel.studentGrade.value ?: ""
        val kor = viewModel.studentKor.value ?: ""
        val eng = viewModel.studentEng.value ?: ""
        val math = viewModel.studentMath.value ?: ""

        // 이름
        if (name.isEmpty()) {
            return false
        }

        // 학년
        if (grade.isEmpty()) {
            return false
        }

        // 국어
        if (kor.isEmpty()) {
            return false
        }

        // 영어
        if (eng.isEmpty()) {
            return false
        }

        // 수학
        if (math.isEmpty()) {
            return false
        }

        return true
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
        // toolbar 설정
        binding.apply {
            toolBarAdd.apply {
                inflateMenu(R.menu.menu_add)
            }
        }

    }

    // Event 설정
    fun settingEvent() {
        binding.apply {
            toolBarAdd.apply {
                // navigationIcon 설정
                setNavigationOnClickListener {
                    // 뒤로가기
                    removeFragment()
                }
                // menu
                setOnMenuItemClickListener { menu ->
                    when (menu.itemId) {
                        R.id.menuitem_add_save -> {
                            // 유효성 검사
                            val result = validateInput()

                            if (result) {
                                saveData()
                                // 뒤로가기
                                removeFragment()
                            }

                        }
                    }
                    true
                }
            }
        }
    }

    // 데이터 저장
    fun saveData() {
        try {
            CoroutineScope(Dispatchers.Main).launch {
                // 입력 요소를 가져온다
                val name = viewModel.studentName.value ?: ""
                val grade = viewModel.studentGrade.value!!.toInt()
                val kor = viewModel.studentKor.value!!.toDouble()
                val eng = viewModel.studentEng.value!!.toDouble()
                val math = viewModel.studentMath.value!!.toDouble()

                // 저장할 데이터를 만든다
                val data = StudentInfo(name, grade, kor, eng, math)

                // DB에 전송한다
                val sequence = withContext(Dispatchers.IO) {
                    //DAO 함수 호출
                    AddDao.getSequence()
                }
                Log.d("test1234", "${sequence}")


            }

        } catch (e: Exception) {
            Log.e("AddFragment", "${e.message}")
        }

    }


    fun removeFragment() {
        parentFragmentManager.popBackStack(
            FragmentName.ADD_FRAGMENT.str,
            FragmentManager.POP_BACK_STACK_INCLUSIVE
        )
    }

}