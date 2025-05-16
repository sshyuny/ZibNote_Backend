# API 응답 코드 스타일 지정 및 리팩토링

## 🍇 배경
- API 응답시 정해진 객체(`ApiResponse`)를 항상 메시지 바디에 넣어 반환하며, 이 객체의 구조와 프로젝트 API 응답 코드 스타일을 정하고 리팩토링하는 과정을 정리한다.

## 🍇 내용

### 1. 응답 객체 코드 필드에 Enum 사용
- `ApiResponse`에서 `code` 필드의 데이터형을 String에서 Enum으로 변경함!
```java
public class ApiResponse<T> {
    private ResponseCode code;
    private String message;
    private T data;
}
```

### 2. 응답 메시지에 Enum 사용하여 관리
- API 응답 메시지에 Enum을 활용하여 한 곳에서 통일성 있게 관리될 수 있도록 함.
```java
public enum ResMessage {
    SUCCESS("정상 처리되었습니다."),
    ERROR("오류가 발생했습니다");
}
```
