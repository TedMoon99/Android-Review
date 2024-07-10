# Android-Review
# Android_Review_Test

---

### 클래스 / 객체 / 인스턴스

+ 클래스는 객체를 만들어 내기 위한 _설계도_, 혹은 _틀_ </br> 연관되어 있는 변수와 메서드의 집합</br>


+ 객체는 소프트웨어 세계에 구현할 대상</br> 클래스에 선언된 모양 그대로 생성된 실체
  + 객체는 **클래스의 인스턴스**라고도 함.  

+ 인스턴스는 설계도를 바탕으로 소프트웨어 세계에 구현된 **구체적인 실체**</br> 즉, 객체를 소프트웨어에 실체화하면 인스턴스.
  + 인스턴스는 객체에 포함된다 볼 수 있으며, 추상적 개념과 구체적 객체 사이의 _관계_ 에 초점을 맞출 경우에 사용함. (ex '객체는 클래스의 인스턴스')

## 프래그먼트의 생명주기

+ CREATED - STARTED - RESUMED - STARTED - CREATED - DESTROYED 순으로 생성 후 소멸</br></br>
  + onAttach() : 프래그먼트가 FragementManager에 추가되고, 호스트 액비티에 연결될 때 호출. 이 시점부터 프래그먼트는 활성화됨.</br></br>
  + onCreate() :  프래그먼트가 FragmentManager에 추가 된 후 호스트 액티비티에 attach 될 때 호출. _아직 View가 생성되지 않았기 때문에_ View 관련 작업은 이후에.</br>
    프래그먼트 생성 시 필요한 값들 초기화 및 인자로 전달된 데이터 처리.</br></br>
  + onCreateView() : 프래그먼트가 View를 그리기 위한 _layout을 inflate_ 하는 작업을 수행. 반환값으로 받는 View가 화면에 출력됨.</br></br>
  + onViewCreated() : 앞서 반환된 View가 파라미터로 전달됨. 
    이 시점부터 FragmentView의 생명주기가 _INITIALIZED_ 상태로 업데이트되었기 때문에 **View 초기값 설정**, 혹은 LiveData Observing, RecyclerView의 Adapter 초기화 작업 등을 수행.</br></br>
  + onViewStateRestored() : 저장해둔 모든 state 값이 프래그먼트의 View 계층구조에 복원되었을 때 호출. _ViewLifeCycleOwner_ 는 이때 INITIALIZED 상태에서 CREATED 상태로 변경되었음을 알림.</br></br>
  + onStart() : 프래그먼트가 사용자에게 보여질 수 있을 때 호출됨. 화면이 사용자에게 보여지기 전에 필요한 작업, 예를 들어 애니메이션 시작 같은 작업 등을 수행.
    이 시점부터 child FragmentManager를 통해 FragmentTransaction을 안전하게 수행할 수 있음.</br></br>
  + onResume() : 프래그먼트가 보이는 상태에서 Animator와 Transition 효과가 종료되고, 프래그먼트가 사용자와 상호작용할 수 있을 때 호출.
    사용자와의 상호작용을 시작하거나 세부적인 화면을 업데이트하는 작업 수행.</br></br>
  + onPause() : 프래그먼트가 focus를 받지 않을 때 호출. 현재 진행중인 작업 일시 중단, 필요한 상태 저장 등의 작업 수행.</br></br>
  + onStop() : 프래그먼트가 더 이상 사용자에게 보이지 않을 때 호출. 리소스 해제 등의 작업 수행. 프래그먼트와 View의 Lifecycle은 CREATED 상태가 됨.
    onSaveInstanceState() 함수보다 먼저 호출되어 onStop()이 FragmentTransaction을 안전하게 수행할 수 있는 마지막 지점.</br></br>
  + onDestroyView() : 모든 exit animation과 transition이 완료되고 프래그먼트가 화면으로부터 벗어났을 경우, Fragment View의 LifeCycle은 DESTROYED가 되고 onDestroy() 호출.
    가비지 컬렉터에 의해 수거될 수 있도록 **Fragment View에 대한 모든 참조가 제거되어야 함.**</br></br>
  + onDestroy() : 프래그먼트가 제거되거나 FragmentManager가 destroy 됐을 경우, 프래그먼트 LifeCycle은 DESTROYED 상태가 되며 끝을 알림.
    onAttach()가 onCreate() 이전에 호출됐던 것처럼 onDestroy() 이후에 onDetach() 호출.