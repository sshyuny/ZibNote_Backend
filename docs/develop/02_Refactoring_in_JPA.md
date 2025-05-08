# JPA 사용 방법 변경 및 리팩토링

## 🍏 배경
- JPA 사용하는 구체적인 방법을 조금씩 리팩토링함

## 🍏 내용

##  1. 공통 필드의 자동 변경 및 자동 필터링 기능 추가 vs. 수동 진행
- `createdAt`, `updatedAt`, `isDeleted`는 해당 프로젝트에서 여러 엔티티에 공통으로 사용되는 필드들이다.
- 이러한 공통 필드를 대상으로, 자동으로 변경 및 (조회에서) 필터링 되도록 JPA에서 제공해주는 설정을 사용할지 고민했었다.
- 여러 장단점이 있겠지만 우선 적용!

#### 1.1. 수정 내용
- `@PrePersist`와 `@PreUpdate`를 이용한 공통 필드 값 자동 반영
- 엔티티에 `@SQLRestriction("is_deleted = 0")`를 붙여 삭제 데이터 자동 필터링 기능 추가

#### 1.2. 수정 방법
기존 엔티티들은 추상 클래스 `BaseEntity`를 상속하는 방법을 통해 공통 필드들을 처리하고 있었기 때문에 아래 단계를 밟아 일괄 수정을 진행한다.
1. `BaseEntity`와 유사한 `AutoFillBaseEntity`에 생성하여 원하는 JPA 설정 내용 추가!
2. 기존 엔티티들이 하나씩 `AutoFillBaseEntity`를 상속하도록 수정
3. 관련된 Service, Repository, Domain 코드 수정
4. 마지막으로 `BaseEntity`를 제거하고, `AutoFillBaseEntity` 이름을 `BaseEntity`로 변경!
