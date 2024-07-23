package com.example.android_review05_baek08102.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.android_review05_baek08102.dao.StudentDao
import com.example.android_review05_baek08102.model.StudentData
import com.example.android_review05_baek08102.R
import com.example.android_review05_baek08102.utils.FragmentName
import com.example.android_review05_baek08102.viewmodel.InputViewModel
import com.example.android_review05_baek08102.databinding.DialogAddBinding
import com.example.android_review05_baek08102.databinding.FragmentInputBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.*

class InputFragment : Fragment() {
    private lateinit var binding: FragmentInputBinding
    private val viewModel: InputViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // databinding 사용 시 화면 출력시키는 방법
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_input, container, false)
        binding.inputViewModel = viewModel // 적용시킬 viewModel 연결
        binding.lifecycleOwner = this // 현재 fragment를 lifecycleOwner로 지정

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        settingView()
        settingEvent()
        emptyInput()
        settingError()
    }

    // 뷰 설정
    fun settingView() {
        binding.apply {
            inputToolbar.apply { // 툴바 설정
                setTitle("학생 정보 입력")
                setNavigationIcon(R.drawable.arrow_back_24px)
                inflateMenu(R.menu.menu_confirm)
            }
        }
        hideErrorIcon() // 에러 아이콘 숨김
    }

    // 이벤트 설정
    fun settingEvent() {
        binding.apply {
            inputToolbar.apply {
                setNavigationOnClickListener {
                    removeFragment()
                }
                setOnMenuItemClickListener { item ->
                    when (item.itemId) {
                        R.id.menu_item_confirm -> { // menu item 클릭 시

                            // 유효성 검사 함수의 반환값 result로 받은 후
                            val result = validateInput()

                            // 전달받은 유효성 검사 결과에 따른 작업 수행
                            processByValidate(result)
                        }
                    }
                    true
                }
            }
        }
    }

    // viewModel로 양방향 입출력 구현에 따라
    // fragment 화면 진입 시 기존에 남아있던 입력값 초기화
    fun emptyInput() {
        viewModel.apply {
            name.value = ""
            grade.value = ""
            koreanScore.value = ""
            englishScore.value = ""
            mathScore.value = ""
        }
    }

    // firestore에 입력값 저장하는 함수
    fun saveInput() {
        // 메인 스레드에서 코루틴이 실행되도록
        CoroutineScope(Dispatchers.Main).launch {
            try {
                // 아래 두 작업은 백그라운드 스레드에서 실행

                // firestore로부터 시퀀스 데이터 받아오고
                val studentSequence = withContext(Dispatchers.IO) { StudentDao.getSequence() }
                // 받아온 시퀀스 데이터를 바탕으로 firestore의 시퀀스 업데이트
                withContext(Dispatchers.IO) { StudentDao.updateSequence(studentSequence + 1) }

                val index = studentSequence + 1

                // 사용자가 입력한, InputViewModel에 저장된 데이터
                val name = viewModel.name.value!!
                val grade = viewModel.grade.value!!.toInt()
                val korean = viewModel.koreanScore.value!!.toDouble()
                val english = viewModel.englishScore.value!!.toDouble()
                val math = viewModel.mathScore.value!!.toDouble()

                val total = round((korean + english + math) * 10) / 10
                val average = round((total / 3) * 10) / 10

                // StudentData 타입으로 담아서
                val data = StudentData(index, name, grade, korean, english, math, total, average, true)

                // 역시 백그라운드 스레드에서
                // Dao통하여 firestore에 저장
                withContext(Dispatchers.IO) { StudentDao.inputStudentData(data) }

                // 작업 종료 시 화면 종료
                removeFragment()

            } catch (e: Exception) {
                Log.e("saveInput", "데이터 저장 실패 : ${e.message}")
            }
        }
    }

    // error 출력을 livedata의 observe 활용하여 실시간으로 가능케함.
    // 따라서 이전처럼 유효성 검사를 통합하여 실시할 필요 없이
    // 데이터 입력만 막아주면 됨으로 각각의 검사 구문에서 false를 반환하여도 됨.
    fun validateInput(): Boolean { // 입력값 유효성 검사

        // dataBinding을 통해 양방향 입출력을 구현해놓은 viewModel로부터 입력값을 받아온다
        val name = viewModel.name.value ?: "" // 받아온 값이 null이면 빈 string으로
        val grade = viewModel.grade.value ?: ""
        val korean = viewModel.koreanScore.value ?: ""
        val english = viewModel.englishScore.value ?: ""
        val math = viewModel.mathScore.value ?: ""

        Log.d(
            "validateInput",
            "name : $name, grade : $grade, korean : $korean, english : $english, math : $math"
        )

        if (name.isEmpty() || name.length < 2 || name.length > 5) { // 입력 완료 조건에 부합하지 못할 시
            return false // false 반환 후 함수 종료
        }
        if (grade.isEmpty() || grade.toIntOrNull() == null || grade.toInt() < 1 || grade.toInt() > 6) {
            // toIntOrNull() 은 입력값을 Int 타입으로 변경할 수 없을 시 null 반환, 즉 입력 타입이 잘못되면 false 반환
            return false
        }
        if (korean.isEmpty() || korean.toDoubleOrNull() == null || korean.toDouble() < 0.0 || korean.toDouble() > 100.0) {
            return false
        }
        if (english.isEmpty() || english.toDoubleOrNull() == null || english.toDouble() < 0.0 || english.toDouble() > 100.0) {
            return false
        }
        if (math.isEmpty() || math.toDoubleOrNull() == null || math.toDouble() < 0.0 || math.toDouble() > 100.0) {
            return false
        }
        // 모든 검사 통과 시 true 반환 후 함수 종료
        return true
    }

    // 사용자의 입력값을 observe를 통하여 실시간으로 감시할 수 있기 때문에
    // 유효성 검사와 error를 관리하는 함수를 별도로 세팅
    fun settingError() { // error 설정하는 함수
        binding.apply {

            inputEditTextName.doAfterTextChanged { // 화면 첫 진입 시 에러 출력 막음 (텍스트 입력 시점부터 출력)
                viewModel.name.observe(viewLifecycleOwner) { name ->// observe를 통해 livedata 타입의 데이터 실시간 감시
                    if (name.isNotEmpty() && name != null) { // 입력을 받았고, 받은 입력값이 null이 아니라면
                        if (name.length in 2..5) {
                            inputInputLayoutName.error = null
                        } else {
                            inputInputLayoutName.error = "이름은 두 자에서 다섯 자 사이여야 합니다"
                        }
                    } else {
                        inputInputLayoutName.error = "이름을 입력해주세요"
                    }
                }
            }

            inputEditTextGrade.doAfterTextChanged {
                viewModel.grade.observe(viewLifecycleOwner) { grade ->
                    if (grade.isNotEmpty() && grade != null) {
                        if (grade.toInt() in 1..6) {
                            inputInputLayoutGrade.error = null
                        } else {
                            inputInputLayoutGrade.error = "학년은 1학년에서 6학년 사이여야 합니다"
                        }
                    } else {
                        inputInputLayoutGrade.error = "학년을 입력해주세요"
                    }
                }
            }

            inputEditTextKorean.doAfterTextChanged {
                viewModel.koreanScore.observe(viewLifecycleOwner) { korean ->
                    if (korean.isNotEmpty() && korean != null) {
                        if (korean.toDouble() in 0.0..100.0) {
                            inputInputLayoutKorean.error = null
                        } else {
                            inputInputLayoutKorean.error = "점수는 0점에서 100점 사이여야 합니다"
                        }
                    } else {
                        inputInputLayoutKorean.error = "점수를 입력해주세요"
                    }
                }
            }

            inputEditTextEnglish.doAfterTextChanged {
                viewModel.englishScore.observe(viewLifecycleOwner) { english ->
                    if (english.isNotEmpty() && english != null) {
                        if (english.toDouble() in 0.0..100.0) {
                            inputInputLayoutEnglish.error = null
                        } else {
                            inputInputLayoutEnglish.error = "점수는 0점에서 100점 사이여야 합니다"
                        }
                    } else {
                        inputInputLayoutEnglish.error = "점수를 입력해주세요"
                    }
                }
            }

            inputEditTextMath.doAfterTextChanged {
                viewModel.mathScore.observe(viewLifecycleOwner) { math ->
                    if (math.isNotEmpty() && math != null) {
                        if (math.toDouble() in 0.0..100.0) {
                            inputInputLayoutMath.error = null
                        } else {
                            inputInputLayoutMath.error = "점수는 0점에서 100점 사이여야 합니다"
                        }
                    } else {
                        inputInputLayoutMath.error = "점수를 입력해주세요"
                    }
                }
            }
        }
    }

    // snackbar 출력 함수
    fun showSnackbar() {
        val snackbar = Snackbar.make(requireView(), "정보 입력이 완료되었습니다", Snackbar.LENGTH_SHORT)
        snackbar.animationMode = Snackbar.ANIMATION_MODE_FADE // snackbar 애니메이션 설정
        snackbar.show()
    }

    // dialog 출력 함수
    fun showDialog() {
        val builder = MaterialAlertDialogBuilder(requireContext())
        val dialogBinding = DialogAddBinding.inflate(layoutInflater) // dialog_add.xml을 binding으로 inflate

        builder.apply {
            setTitle("정보 입력 미완료")
            setMessage("입력되지 않은 값이 존재합니다")
            setView(dialogBinding.root)
            setPositiveButton("확인") { dialog, id ->
                // 기본적으로 dialog 버튼 클릭 시 dialog는 종료되지만
                // 추가적인 동작 필요 시 작성
            }
        }
        builder.show()
    }

    // 유효성 검사 통과 여부에 따른 작업 분기
    fun processByValidate(result: Boolean) {
        if (result) { // 유효성 검사 통과 시

            showSnackbar() // snackbar 띄우고
            saveInput() // db에 입력받은 데이터 저장한다
        } else { // 유효성 검사 통과 실패 시
            showDialog() // dialog만 띄운다
        }
    }

    // 에러 아이콘 표시하지 않도록 하는 함수
    fun hideErrorIcon() {
        binding.apply {
            inputInputLayoutName.errorIconDrawable = null
            inputInputLayoutGrade.errorIconDrawable = null
            inputInputLayoutKorean.errorIconDrawable = null
            inputInputLayoutEnglish.errorIconDrawable = null
            inputInputLayoutMath.errorIconDrawable = null
        }
    }

    // Fragment 화면 삭제 함수
    fun removeFragment() {
        parentFragmentManager
            .popBackStack(FragmentName.Input_Fragment.name, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }
}