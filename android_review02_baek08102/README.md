# Android_Review02_Baek08102

---

2번 문제 MainFragment 자세한 색인  
```kotlin

package com.example.android_review02_baek08102

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android_review02_baek08102.databinding.FragmentMainBinding
import com.google.android.material.divider.MaterialDividerItemDecoration

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding // fragment_main.xml의 바인딩
    private val dataList: MutableList<String> = mutableListOf() // Name 클래스의 데이터 입출력 위한 Mutablelist
    private val filteredList: MutableList<String> =
        mutableListOf() // 검색어에 따라 출력하는 데이터 위한 MutableLIst

    // 이곳엔 바인딩과 관련된 것들 선언
    // onCreateView는 프래그먼트의 레이아웃 inflate하여 View 객체 반환
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        // inflater를 통하여 XML 파일에 정의된 레이아웃을 화면에 표시될 수 있는 뷰 객체로 inflate함
        // 그렇게 inflate 된 객체가 container의 뷰 그룹에 추가
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMainBinding.inflate(inflater)
        //fragment_main 바인딩 사용 위한 binding 객체

        return inflater.inflate(R.layout.fragment_main, container, false)
        // onCreateView의 반환값인 View 타입의 뷰 객체 반환
        // 여기선 fragment_main.xml 을 inflate, 그렇게 inflate한 객체가 추가된 container
    }

    // 이곳엔 일반적 함수들 호출
    // onViewCreated는 프래그먼트 뷰가 생성된 후 추가적인 초기화 수행하는데에 사용, 주로 뷰 요소
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        settingView()
        settingEvent()

    }

    // 뷰 설정 위한 함수
    fun settingView() {
        binding.apply {
            val context = requireContext()
            // 현재 프래그먼트의 Context 가져옴

            // 구분선 생성
            val deco = MaterialDividerItemDecoration(context, LinearLayoutManager.VERTICAL).apply {
                // 마지막 구분선 제거
                isLastItemDecorated = false
            }
            // 리싸이클러뷰 연결
            recyclerViewMain.apply {
                layoutManager = LinearLayoutManager(context)
                addItemDecoration(deco)
            }
        }
    }

    // 이벤트 설정 위한 함수

    fun settingEvent() {
        binding.apply {
            // 추가 버튼 클릭 시
            buttonMainAdd.setOnClickListener {
                val resultName = validateInput()

                if (resultName) { // 유효성검사 통과 시
                    val name = textInputEditTextMainName.text.toString()

                    // 데이터 입력
                    dataList.add(name)

                    // 어댑터 연결
                    recyclerViewMain.adapter = MyAdapter(dataList)

                    // 어댑터에게 데이터 변경 알림
                    recyclerViewMain.adapter?.notifyDataSetChanged()

                    //입력 요소 초기화
                    textInputEditTextMainName.text = null
                }
            }

            // 텍스트 실시간 리스너
            textInputEditTextMainSearch.addTextChangedListener { search ->
                val newFilteredList = dataList.filter { it.contains(search.toString()) }
            }
        }
    }

    // 유효성 검사 위한 함수
    fun validateInput(): Boolean {
        var resultName = false
        binding.apply {
            // 입력된 이름 받아온다
            val name = textInputEditTextMainName.text.toString()

            if (name.isNotEmpty()) { // 값이 입력 되었고
                if (name.length in 2 until 6) { // 입력값이 2자 이상 5자 미만이라면
                    // 오류 해제
                    textInputLayoutMainName.error = null
                    resultName = true
                } else {
                    // 오류 설정
                    textInputLayoutMainName.error = "올바른 이름 형식이 아닙니다."
                    resultName = false
                }
            } else {
                //오류 설정
                textInputLayoutMainName.error = "이름을 입력해주세요."
                resultName = false
            }
            return resultName
        }
    }
}

```

```kotlin
package com.example.android_review02_baek08102

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// 어댑터 구현
//String 타입 데이터를 관리하기 위한 data class Name의 리스트를 받음
class MyAdapter(private val nameData: List<String>) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() { //에러

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView

        init {
            name = view.findViewById(R.id.textView_row_main) //에러
        }
    }

    // ViewHolder 생성 시 호출
    // row_main.xml inflate하여 View 객체로 만들고 MyViewHolder 객체 생성하여 반환
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_main, parent, false)

        return MyViewHolder(view) //에러
    }

    // 출력할 항목 개수 반환
    override fun getItemCount(): Int {
        return nameData.size
    }

    // ViewHolder에 Name data class의 데이터 바인딩, 재사용시 호출
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.name.text = nameData[position]
    }
}

```