# Android_Review05_TedMoon

---

## 문제 사진 첨부

### 1. 메인 화면
![img.png](img.png)

### 2. 입력 화면
![img_1.png](img_1.png)

### 3. 정보 조회 화면 
![img_2.png](img_2.png)

## 필요한 개념 정리

---
#### Menu

- showAsAction : 메뉴 항목을 Toolbar에 배치할 것인지를 설정한다
  - always : 항상 Toolbar에 배치한다
  - ifRoom : `공간이 허락할 경우`에 Toolbar에 배치한다
  - never : Toolbar에 배치하지 않는다 
  - withText : 아이콘이 설정되어 있을 경우 아이콘이 보여지고 공간이 허락되면 menu의 title에 설정된 문자열도 보인다 

- setTitleTextColor() : 타이틀 문자열 색상을 설정한다


- Context Menu : View를 길게 누르면 타나타는 메뉴
- Popup Menu : 개발자가 코드를 통해 원하는 View에 띄우는 메뉴

#### Message
- Toast : 잠깐 보여줬다가 사라지는 메시지
  - 어플 화면이 떠있지 않을 때 보여질 때 주로 사용한다
- SnackBar : 잠깐 보여줬다가 사라지는 메시지
  - 어플 화면이 떠있을 경우 사용한다
  - Toast와 다르게 지속적으로 띄울 수 있으며 Action을 넣어 이벤트를 설정할 수 있다
- Dialog : 애플리케이션 화면에 메시지를 띄울 때 사용한다
  - 사용자가 버튼을 눌러야지만 없어지는 메시지이다
- Notification : 단말기 알림 메시지 창에 나타나는 메시지이다
  - 메시지를 통해 어플 실행을 유도하기 위해 사용한다
  - 안드로이드 8 이상부터는 메시지를 관리하는 채널을 등록해야 한다
  - 안드로이드 13 이상부터는 알림메시지를 보여줄 수 있는 권한을 확인 받아야 한다
  
