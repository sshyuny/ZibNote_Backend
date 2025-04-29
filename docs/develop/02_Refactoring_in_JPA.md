# 배경
- JPA 사용하는 구체적인 방법을 조금씩 리팩토링함

# 내용

##  1. 공통 필드의 자동 변경 및 자동 필터링 기능 추가 vs. 수동 진행
createdAt, updatedAt, isDeleted는 해당 프로젝트에서 여러 엔티티에 공통으로 사용되는 필드들이다.
자동으로 변경 및 (조회에서) 필터링 되도록 JPA에서 제공해주는 설정을 사용하는 것에는 장단점이 있겠지만, 우선 적용해봄!
### 수정 내용
- @PrePersist와 @PreUpdate를 이용한 공통 필드 값 자동 반영
- 엔티티에 @SQLRestriction("is_deleted = 0")를 붙여 삭제 데이터 자동 필터링 기능 추가
### 수정 방법
- 기존에 엔티티들이 상속한 추상 클래스 BaseEntity와 유사한 AutoFillBaseEntity 생성
- 기존 엔티티 하나씩 AutoFillBaseEntity를 상속하도록 수정
- 관련 Service, Repository, domain 코드 수정
- 마지막에 AutoFillBaseEntity 이름을 BaseEntity로 변경

