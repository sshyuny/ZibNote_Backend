# 엔티티 PK 데이터 타입을 Long에서 UUID로 전환

## 🫑 배경
- 기존에는 테이블에서 PK의 데이터형으로 `BIGINT`를, 엔티티에서는 `Long`을 사용하고 있었기 때문에, 클라이언트에 노출시 순서가 유추되어 보안적으로 아쉬움이 있었다.

## 🫑 내용

### 1. 수정 내용
- 보안적으로 중요하다고 판단되는 내용의 테이블일 경우 PK를 UUID(MySQL의 경우 BINARY)로 수정하고, JPA 엔티티와 관련 메서드를 수정한다.
```sql
TABLE SEARCH_STRUCTURE (
    search_structure_id BINARY(16) NOT NULL PRIMARY KEY
);
```
```java
public class SearchStructureEntity extends BaseEntity {
    
    @Id @UuidGenerator
    @Column(columnDefinition = "BINARY(16)")
    private UUID searchStructureId;

}
```

### 2. 유의사항
- 데이터 생성시 JPA 엔티티에서 UUID 데이터 타입의 PK를 생성하여 넣어주는 기존 방식이 deprecated 되었다.
    - 기존 방식 `@GenericGenerator` 어노테이션은 deprecatated됨.
    - 현재 프로젝트에서 사용하는 Hibernate 6.6 버전에서는 새로운 방식인 `@IdGeneratorType` 기반 방식이 권장되어 `@UuidGenerator` 사용함.

### 참고 문서
- [IdGeneratorType docs](https://docs.jboss.org/hibernate/orm/6.6/javadocs/org/hibernate/annotations/IdGeneratorType.html)
- [UuidGenerator docs](https://docs.jboss.org/hibernate/orm/6.6/javadocs/org/hibernate/annotations/UuidGenerator.html)
