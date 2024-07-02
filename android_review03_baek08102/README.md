# Android-review


## android_review03_baek08102

---

### RecyclerAdapter

사용자가 정의한 어댑터 클래스는 생성자 파라미터로 세 가지를 전달 받을 수 있음.<br/>

1. **itemList: List&lt;CustomData&gt;** : 어댑터가 관리할 데이터(List), 즉 RecyclerView에 표시할 데이터를 전달
2. **context: Context** : 어댑터가 리소스 접근, 혹은 컨텍스트 작업 수행할 수 있도록 _'context'_ 혹은 _'resource'_ 전달
3. **clickListener: <CustomData> -> Unit** : RecyclerView의 각 아이템이 클릭되었을 때의 동작 정의하는 클릭리스너 


### Adapter 클래스에 필수로 구현해야 하는 세 가지 함수

1. **onCreateViewHolder** : 새로운 ViewHolder 객체를 생성하며 ItemLayout을 inflate 함
2. **getItemCount** : 매개변수로 받은 데이터 리스트의 크기를 반환, 즉 RecyclerView가 몇 개의 아이템을 표시할 건지 결정하여 줌
3. **onBindViewHolder** : 매개변수로 받은 데이터 리스트에서 데이터를 가져와 ViewHolder에 바인딩함