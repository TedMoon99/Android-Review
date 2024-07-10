# Android-review

## android_review04_baek08102

---

### **WindowInsets**

* **WindowInset**이란 window contents에 대한 _insets_ 집합을 설명함.
  * 여기서 **insets**이란, Rectangle의 네 모서리의 크기를 갖고 있는 객체.  
</br>

* _WindowInsets_ 에는 <br/>
  * System window insets
  * Tappadble element insets 
  * Gesture insets
  * Stable insets </br>
  이렇게 4 가지가 포함됨.

이 중 &lt;**System window insets**&gt;를 구한다면 안드로이드 시스템의 윈도우 부분인 상태바(status bar)와 네비게이션 바의 높이가 다음과 같이 insets에 담겨 나옴.</br>
`Insets{left=0, top=66, right=0, bottom=32`
