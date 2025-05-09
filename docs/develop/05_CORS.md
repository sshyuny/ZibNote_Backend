# CORS를 처리하는 다양한 방법 정리

## 🍊 배경
- 로그인 처리 로직 위치를 인터셉터에서 필터로, 다시 인터셉터로 옮기며 기존 설정했던 CORS 처리 방법이 적용되지 않아 수정이 필요했다.
- CORS를 허용하는 여러 방법을 적용해보았으며, 그 과정에서 Spring과 Java에서 처리하는 CORS 허용 방법을 다양하게 알 수 있어 정리한다.
- CORS를 허용하는 여러 방법들이 있지만 각각 특징이 다르기 때문에 상황에 맞춰서 적절히 사용해야한다!

## 🍊 방법

### 1. CorsRegistry 등록 방법
1. 요청 메소드가 OPTIONS일 경우 전부 넘어가도록 인터셉터를 수정한다.
```java
@Component
public class LoginCheckInterceptor implements HandlerInterceptor {
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (request.getMethod().equalsIgnoreCase("OPTIONS")) {
            return true; // 무조건 통과
        }

        // 로그인 확인 로직
    }

}
```
2. @Configuration에서 addCorsMappings를 설정!
```java
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/*")
                .allowedOrigins("http://localhost:3000")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowCredentials(true);
    }
    
}
```
- 이렇게 하면, 클라이언트의 요청이 인터셉터를 지나 DispatcherServlet으로 잘 전달되고, DispatcherServlet은 addCorsMappings에서 설정한 CorsRegistry에 맞춰 CORS를 적절히 허용할 수 있다!
- DispatcherServlet 이후 적용되기 때문에 인터셉터에는 잘 적용되지만 필터가 있을 경우 CORS 처리가 되지 않는다.

### 2. Filter에도 적용되는 설정 (1) CorsFilter 적용
- Fiilter를 사용할 경우에도 CORS 허용 처리를 하고 싶다면, Spring의 CorsFilter를 등록하여 간편하게 해결할 수 있다.
```java
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilter() {
        // (설정 내용 추가)
    }
    
}
```

### 3. Filter에도 적용되는 설정 (2) 직접 수동으로 설정
- CorsFilter 대신에 직접 커스텀 필터를 사용할 수 있다.
1. CORS 허용하는 내용을 담은 응답 메시지를 보내는 필터를 만들고
```java
public class CustomCorsFilter extends OncePerRequestFilter  {
    
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // CORS 허용 내용 헤더에 직접 넣어주기!
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
        response.setHeader("Access-Control-Allow-Credentials", "true");

        // OPTIONS 메소드일 경우 바로 응답
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        filterChain.doFilter(request, response);
    }
}
```
2. 해당 필터를 다른 커스텀 필터들보다도 앞에 지나가게 한다.
```java
@Configuration
public class WebConfig implements WebMvcConfigurer {
    
    @Bean
    public CustomCorsFilter customCorsFilter() {
        return new CustomCorsFilter();
    }

    @Bean
    public FilterRegistrationBean<CustomCorsFilter> customCorsFilterRegister() {
        FilterRegistrationBean<CustomCorsFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(customCorsFilter());
        registrationBean.addUrlPatterns("/*");
        registrationBean.setOrder(0);  // 가장 앞에!
        return registrationBean;
    }
    
}
```

## 🍊 CorsRegistry가 Filter에는 적용되지 않는 이유
- 여러 방법을 사용해보던 중 궁금증이 들었다! 요청 메시지가 인터셉터를 지나처 DispatcherServlet에 가고, 이어 CorsProcessor로 넘어가며 CORS 처리가 이뤄지는 거라면, Filter도 인터셉터처럼 메시지를 바로 넘겨버리면 되지 않을까?
- Filter에서 이 방법을 사용할 수 없는 이유는 (1) 스프링에서 CORS를 처리하는 방식과 (2) Filter를 거쳐 DispatcherServlet에 도달한 요청은 DispatcherServlet에 바로 들어온 요청과 조금 다르게 처리된다는 점을 알아야 이해할 수 있다.
    1. CorsProcessor는 HandlerMapping이 인식하는 경로들을 대상으로만 CORS 처리를 진행한다. 스프링 설정에서 addCorsMappings를 통해 CorsRegistry를 등록하면 컨트롤러에 등록된 매핑 정보가 없더라도 CORS의 대상이 되어 CorsProcessor가 처리할 수 있다.
    2. Filter를 거친 요청은 DispatcherServlet에서 바로 HandlerMapping에 등록된 정보로 넘겨버리기 때문에 CorsProcessor를 거치지 않게 된다.

## 🍊 주의점
- 위 세 방법 중 어느 하나라도 적용을 하게되면, 아래 예외를 만날 수 있다.
```
Servlet.service() for servlet [dispatcherServlet] in context with path [] threw exception [Request processing failed: java.lang.IllegalArgumentException: Name for argument of type [java.lang.Long] not specified, and parameter name information not available via reflection. Ensure that the compiler uses the '-parameters' flag.] with root cause
```
- 원인은 Controller 메소드에서 `@RequestParam Long id`를 인식할 수 없어서 생긴 것으로, 이렇게 `@RequestParam(value = "id") Long id` 직접 value를 명시해주면 해결된다.
```java
// 컨트롤러의 메소드
@GetMapping("/list")
public ResponseEntity<ApiResponse<List<SearchStructureResDto>>> getList(
    @RequestParam(value = "id") Long id) {  // 직접 value 명시해주기!
}
```
- 이런 예외는, CORS 처리 과정 중에 요청 메시지가 래핑되는 등 중간 처리 과정을 거치며 스프링이 url 파라미터를 인식할 수 없게 되며 발생되는 것으로 추측된다.
