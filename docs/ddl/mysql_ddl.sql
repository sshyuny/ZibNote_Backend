/* ==== MySQL DDL ==== */

/* DATABASE */
DROP DATABASE IF EXISTS zibnote_db;
CREATE DATABASE zibnote_db;
USE zibnote_db;

/* USERS */
DROP USER IF EXISTS 'zibnote_app'@'localhost', 'zibnote_admin'@'localhost';
CREATE USER 'zibnote_app'@'localhost' IDENTIFIED BY 'pw11';
CREATE USER 'zibnote_admin'@'localhost' IDENTIFIED BY 'pw11';
GRANT ALL PRIVILEGES ON zibnote_db.* TO 'zibnote_app'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE ON zibnote_db.* TO 'zibnote_admin'@'localhost';

exit
mysql -u zibnote_admin -p

/* TABLES */
-- 회원
DROP TABLE IF EXISTS MEMBER;
CREATE TABLE MEMBER (
    member_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    member_name VARCHAR(50) COMMENT '사용자 이름'
);

-- 건물(미리 넣어둔 공식데이터로 사용)
DROP TABLE IF EXISTS STRUCTURE;
CREATE TABLE STRUCTURE (
    structure_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    structure_name VARCHAR(255) COMMENT '건물 이름',  -- 예: 목화아파트, 무궁화아파트
    address VARCHAR(255) COMMENT '건물 주소',
    latitude DECIMAL(10,7),
    longitude DECIMAL(10,7),
    built_year INT COMMENT '설립연도',
    UNIQUE (address)
)

-- 조사(임장 또는 인터넷 조사도 포함)
DROP TABLE IF EXISTS SEARCH;
CREATE TABLE SEARCH (
    search_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    member_id BIGINT,
    description VARCHAR(255) COMMENT '설명', -- 예: 산본역 아파트 2025 임장
    region VARCHAR(255) COMMENT '조사(임장) 지역',  -- 예: 산본역
    start_date DATE COMMENT '조사(임장)기간 시작일',
    end_date DATE COMMENT '조사(임장)기간 종료일',
    FOREIGN KEY (member_id) REFERENCES MEMBER(member_id)
);

DROP TABLE IF EXISTS NOTE_FIELD;
CREATE TABLE NOTE_FIELD (
    note_field_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    member_id BIGINT,
    note_field_name VARCHAR(100) COMMENT '항목명',  -- 예: 세대수, 놀이터, 인도 퀄리티, 차도 퀄리티, 조경, 행인 연령대
    FOREIGN KEY (member_id) REFERENCES MEMBER(member_id)
)

DROP TABLE IF EXISTS EVAL_TYPE;
CREATE TABLE EVAL_TYPE (
    eval_type_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(30) COMMENT '평가명',     -- 예: 별점, 점수, 순위, 해당여부
    code VARCHAR(30) COMMENT '평가타입',     -- 예: STAR, SCORE, RANK, BOOLEAN
    description VARCHAR(255) COMMENT '설명'  -- 예: 1~5 별점, 1~100점, 1~N 순위
)

DROP TABLE IF EXISTS SEARCH_STRUCTURE;
CREATE TABLE SEARCH_STRUCTURE (
    search_structure_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    search_id BIGINT,
    structure_id BIGINT,
    UNIQUE (search_id, structure_id),
    FOREIGN KEY (search_id) REFERENCES SEARCH(search_id),
    FOREIGN KEY (structure_id) REFERENCES STRUCTURE(structure_id)
);

DROP TABLE IF EXISTS SEARCH_STRUCTURE_NOTE;
CREATE TABLE SEARCH_STRUCTURE_NOTE (
    search_structure_id BIGINT,
    note_field_id BIGINT,
    eval_type_id BIGINT,
    eval_value VARCHAR(100) COMMENT '사용자 입력 평가값',
    note TEXT COMMENT '상세 메모',
    PRIMARY KEY (search_structure_id, note_field_id, eval_type_id),
    FOREIGN KEY (search_structure_id) REFERENCES SEARCH_STRUCTURE(search_structure_id),
    FOREIGN KEY (note_field_id) REFERENCES NOTE_FIELD(note_field_id),
    FOREIGN KEY (eval_type_id) REFERENCES EVAL_TYPE(eval_type_id)
);


