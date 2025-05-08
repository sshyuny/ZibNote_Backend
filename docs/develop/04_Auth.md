# 로그인 인증 및 상태 유지 방식 변경 기록

## 🍑 배경
- 로그인 인증 및 상태 유지 방식을 개선하기 위해 여러 방법을 적용해본다.
- 공통 인터페이스인 `AuthUseCase`를 기준으로, 각 방식에 따라 별도의 구현체를 둔다.
```java
public interface AuthUseCase {
    Token login(String name);
    void logout();
    Long getMemberId();
}
```

## 🍑 방법

### 방법 1. 세션 사용
- 내용: 로그인 성공시 세션을 생성하고, 로그아웃시 세션을 파괴한다.
- 구현체: `SessionAuthService`
- 로그인 여부 확인 방식: `LoginCheckInterceptor`를 통해 세션 존재 여부를 확인

### 방법 2. JWT 사용
- 내용: 로그인 성공시 JWT 토큰을 생성하여 클라이언트에 전달한다.
- 구현체: `JwtAuthService`
- 로그인 여부 확인 방식: `JwtAuthFilter`를 통해 JWT를 파싱하여 사용자 정보를 추출한다.
- 특이 사항: 사용한 jjwt 0.12.6 버전에서는 일부 메서드가 deprecated되어, 최신 방식으로 토큰 생성 및 파싱을 처리하였다.
