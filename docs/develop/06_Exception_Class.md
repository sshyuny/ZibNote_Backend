# 예외 클래스 구조 정리

## 🍠 배경
- 예외 클래스의 구조를 정리하여, 예외 처리와 `@ControllerAdvice` 사용을 단순화한다.

## 🍠 내용

### 예외 클래스 계층
```
- RuntimeException
    - ZibnoteRuntimeException
        - UnauthorizedAccessException
        - ResourceNotFoundException (abstract class)
            - MemberNotFoundException
            - SearchNotFoundException
            - SearchStructureNotFoundException
            - SearchStructureNoteNotFoundException
            - StructureNotFoundException
        - InValidRequestException (abstract class)
            - NotValidSearchStructureException
            - NotValidSearchStructureNoteException
        - AlreadyDeletedException
```

### 예외 클래스 위치
```
- domain/
    - common/
        - exception/
            - ZibnoteRuntimeException
            - UnauthorizedAccessException
            - ResourceNotFoundException (abstract class)
            - InValidRequestException (abstract class)
            - AlreadyDeletedException
    - search/
        - exeption/
            - SearchNotFoundException
            - SearchStructureNotFoundExceptioin
            - InvalidSearchStructureException
```
