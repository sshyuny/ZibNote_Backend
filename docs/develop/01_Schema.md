# 배경
- 스키마 설계를 하며 고민 되는 내용들이 생김

# 고민점 및 해결

## 1. 복합키 사용 vs. 대리키 사용
- 테이블 SEARCH_STRUCTURE
```sql
CREATE TABLE SEARCH_STRUCTURE (
    -- search_structure_id BIGINT AUTO_INCREMENT PRIMARY KEY,  -- 대리키 사용?
    search_id BIGINT,
    structure_id BIGINT,
    description TEXT,
    -- UNIQUE (search_id, structure_id),
    -- PRIMARY KEY (search_id, structure_id),  -- 복합키 사용?
    FOREIGN KEY (search_id) REFERENCES SEARCH(search_id),
    FOREIGN KEY (structure_id) REFERENCES STRUCTURE(structure_id)
);

```
- 간단한 테이블이라 복합키 사용도 고려되었음.
- 그러나 SEARCH_STRUCTURE_NOTE 테이블이 해당 SEARCH_STRUCTURE 테이블을 바라보기 때문에 복합키를 사용할 경우 다른 부분에서도 복잡해질 우려가 있었음.
- 결국 대리키를 사용하기로 결정!

## 2. Enum 사용하기 vs. 별도 테이블로 빼기
```sql
CREATE TABLE EVAL_TYPE (
    eval_type_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(30) NOT NULL COMMENT '평가명',     -- 예: 별점, 점수, 순위, 해당여부
    code VARCHAR(30) NOT NULL OMMENT '평가타입',     -- 예: STAR, SCORE, RANK, BOOLEAN
    description VARCHAR(255) COMMENT '설명'  -- 예: 1~5 별점, 1~100점, 1~N 순위
)
```
- 초반에는 평가 타입을 별도 테이블로 만들어서 관리하는 것으로 구상함.
- 그러나 평가 타입이 이후 크게 확장될 가능성이 낮으며 비즈니스 로직에서 관리할 때의 이점을 살리기 위해 Java Enum을 활용하기로 결정!
