# 배경
- 계정 로그인 인증 방식을 변경한다.
- 해당 인증 방식은 `AuthUseCase` 인터페이스가 기준이 되고 각 방법 별 구현체를 따로 둔다.
```java
public interface AuthUseCase {
    Token login(String name);
    void logout();
    Long getMemberId();
}
```

## 방법 1. 세션 사용
- 내용: 로그인 성공시 세션을 생성하고, 로그아웃시 세션을 파괴한다.
- AuthUseCase 구현체: `SessionAuthService`
- 클라이언트의 로그인 확인: `LoginCheckInterceptor`

## 방법 2. JWT 사용
- 내용: 로그인 성공시 JWT 토큰을 발급한다.
- AuthUseCase 구현체: `JwtAuthService`
- 클라이언트의 로그인 확인: `JwtAuthFilter`
- 특이 사항: 사용한 0.12.6 버전에서 여러 메소드들이 deprecated 되어 새로운 버전 메소드를 사용함.
