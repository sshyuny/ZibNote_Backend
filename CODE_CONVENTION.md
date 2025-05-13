# 프로젝트 코드 컨벤션 정리

## 🧀 배경
프로젝트에 코드 컨벤션을 정하여 일관성 있는 스타일로 개발하고 수정/확장/읽기에 쉬운 코드를 지향합니다.

## 🧀 내용

### 1. JPA 엔티티에 ref 메서드 사용
- ID만 들어간 JPA Entity를 생성하는 `ref(Long id)` 정적 메서드를 정의합니다.
- 엔티티 참조용으로서, DB에서 조회되지 않고 영속 상태가 아닌 엔티티입니다.

```java
@Entity
public class MemberEntity extends BaseEntity {
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    public static MemberEntity ref(Long memberId) {
        return MemberEntity.builder()
                    .memberId(memberId)
                    .build();
    }
}
```

### 2. domain model에 onlyId 메서드 사용
- ID만 들어간 domain 객체를 생성하는 `onlyId(Long id)` 정적 메서드를 사용합니다.
- 참조 도메인의 ID만 필요한 UseCase에서 주로 사용됩니다.

```java
public class Member extends BaseFields {
    
    private Long memberId;

    public static Member onlyId(Long memberId) {
        return Member.builder()
                    .memberId(memberId)
                    .build();
    }
}
```

### 3. final 사용
- 프로젝트에서 불변성을 지향하기 위해 `final`을 붙여 변수의 재할당을 방지합니다.
    - 대상: 메서드 파라미터, 지역 변수, 테스트 변수, 도메인 모델의 필드
    - (DTO의 필드는 `@NoArgsConstructor` 사용을 위해 예외)
    - (JPA Entity 필드는 예외)
- 객체의 경우 참조값 불변을 의미하며 내부 필드가 변경되는 상황을 포함합니다.
- 짧은 줄의 코드로 구성된 메서드에도 가급적 붙여 일관성을 유지합니다.
```java
@Repository
public class MemberPersistenceAdapter implements MemberRepository {

    @Override
    public Member findByMemberId(final Long memberId) {

        final MemberEntity memberEntity = memberJpaRepository.findById(memberId)
            .orElseThrow(() -> new MemberNotFoundException());
        // memberEntity 내부 memberId는 변경될 것으로 기대.

        return MemberEntityMapper.toDomain(memberEntity);
    }
}
```
