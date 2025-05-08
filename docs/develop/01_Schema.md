# 스키마 구성시 고민점과 해결 방법

## 🥕 배경
- 프로젝트 초반에 스키마를 구성하고 설계하며 떠오른 고민점을 정리한다.
- 프로젝트를 개발하다가도 더 좋은 설계가 있을지 고민하고 수정!

## 🥕 고민점 및 해결방법

### 1. 복합키 사용 vs. 대리키 사용
- 테이블 `SEARCH_STRUCTURE` 변경 내용
```sql
-- [초기 구상: 복합키 사용]
CREATE TABLE SEARCH_STRUCTURE (
    search_id BIGINT,
    structure_id BIGINT,
    PRIMARY KEY (search_id, structure_id), -- 복합키 사용!
    FOREIGN KEY (search_id) REFERENCES SEARCH(search_id),
    FOREIGN KEY (structure_id) REFERENCES STRUCTURE(structure_id)
);

-- [현재 내용: 대리키 사용]
CREATE TABLE SEARCH_STRUCTURE (
    search_structure_id BIGINT AUTO_INCREMENT PRIMARY KEY, -- 대리키 사용!
    search_id BIGINT,
    structure_id BIGINT,
    FOREIGN KEY (search_id) REFERENCES SEARCH(search_id),
    FOREIGN KEY (structure_id) REFERENCES STRUCTURE(structure_id)
);
```
- 간단한 테이블이라 복합키 사용도 고려되었다.
- 그러나 `SEARCH_STRUCTURE_NOTE` 테이블이 해당 `SEARCH_STRUCTURE` 테이블을 바라보기 때문에 복합키를 사용할 경우 다른 부분에서도 복잡해질 우려가 있었다.
- 결국 대리키를 사용하기로 결정!

### 2. Java Enum 사용하기 vs. 별도 테이블로 빼기
- 초반에는 평가 타입을 별도 테이블 `EVAL_TYPE`을 두어 관리하는 것으로 구상하였다.
```sql
-- 초반 구상한 테이블 EVAL_TYPE (현재 제거함)
CREATE TABLE EVAL_TYPE (
    eval_type_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(30) NOT NULL COMMENT '평가명',     -- 예: 별점, 점수, 순위, 해당여부
    code VARCHAR(30) NOT NULL OMMENT '평가타입',     -- 예: STAR, SCORE, RANK, BOOLEAN
    description VARCHAR(255) COMMENT '설명'  -- 예: 1~5 별점, 1~100점, 1~N 순위
)
```
- 그러나 "평가 타입"의 개수가 많지 않으며, 이를 비즈니스 로직에서 관리할 때의 이점을 살리기 위해 Java Enum을 활용하기로 결정!
- 테이블의 enum은 이후 확장을 위해 사용하지 않음(enum의 내용이 추가될 여지가 있기 때문에)
