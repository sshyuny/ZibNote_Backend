/* ==== MySQL DDL ==== */

/* DATABASE */
DROP DATABASE IF EXISTS zibnote_db;
CREATE DATABASE zibnote_db;
USE zibnote_db;

/* USERS */
DROP USER IF EXISTS 'zibnote_app'@'localhost', 'zibnote_admin'@'localhost';
CREATE USER 'zibnote_app'@'localhost' IDENTIFIED BY 'pw11';
CREATE USER 'zibnote_admin'@'localhost' IDENTIFIED BY 'pw11';
GRANT ALL PRIVILEGES ON zibnote_db.* TO 'zibnote_admin'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE ON zibnote_db.* TO 'zibnote_app'@'localhost';

exit
mysql -u zibnote_admin -p

/* TABLES */
-- 회원
DROP TABLE IF EXISTS MEMBER;
CREATE TABLE MEMBER (
    member_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL COMMENT '사용자 이름',
    created_at DATETIME NOT NULL COMMENT '데이터 생성 시각',
    updated_at DATETIME NOT NULL COMMENT '데이터 수정 시각',
    is_deleted TINYINT(1) NOT NULL DEFAULT 0 COMMENT '삭제됨(0: 존재, 1: 삭제)',
    UNIQUE(name)
);

-- 건물(미리 넣어둔 공식데이터로 사용)
DROP TABLE IF EXISTS STRUCTURE;
CREATE TABLE STRUCTURE (
    structure_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL COMMENT '건물 이름',  -- 예: 목화아파트, 무궁화아파트
    number_address VARCHAR(255) COMMENT '지번 주소',
    road_address VARCHAR(255) COMMENT '도로명 주소',
    latitude DECIMAL(10,7),
    longitude DECIMAL(10,7),
    built_year INT COMMENT '설립연도',
    created_at DATETIME NOT NULL COMMENT '데이터 생성 시각',
    updated_at DATETIME NOT NULL COMMENT '데이터 수정 시각',
    is_deleted TINYINT(1) NOT NULL DEFAULT 0 COMMENT '삭제됨(0: 존재, 1: 삭제)',
    UNIQUE (number_address),
    UNIQUE (road_address)
)

-- 조사(임장 또는 인터넷 조사도 포함)
DROP TABLE IF EXISTS SEARCH;
CREATE TABLE SEARCH (
    search_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    member_id BIGINT,
    title VARCHAR(100) NOT NULL COMMENT '조사 제목',   -- 예: 산본역 아파트 2025 임장
    region VARCHAR(255) COMMENT '조사(임장) 지역',  -- 예: 산본역
    description VARCHAR(255) COMMENT '설명',  -- 예: 스터디원 4명과 함께 임장. / 개인 임장.
    start_date DATE COMMENT '조사(임장)기간 시작일',
    end_date DATE COMMENT '조사(임장)기간 종료일',
    created_at DATETIME NOT NULL COMMENT '데이터 생성 시각',
    updated_at DATETIME NOT NULL COMMENT '데이터 수정 시각',
    is_deleted TINYINT(1) NOT NULL DEFAULT 0 COMMENT '삭제됨(0: 존재, 1: 삭제)',
    FOREIGN KEY (member_id) REFERENCES MEMBER(member_id)
);

DROP TABLE IF EXISTS NOTE_FIELD;
CREATE TABLE NOTE_FIELD (
    note_field_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    member_id BIGINT,
    name VARCHAR(100) NOT NULL COMMENT '항목명',  -- 예: 세대수, 놀이터, 인도 퀄리티, 차도 퀄리티, 조경, 행인 연령대
    description VARCHAR(255) COMMENT '설명',
    created_at DATETIME NOT NULL COMMENT '데이터 생성 시각',
    updated_at DATETIME NOT NULL COMMENT '데이터 수정 시각',
    is_deleted TINYINT(1) NOT NULL DEFAULT 0 COMMENT '삭제됨(0: 존재, 1: 삭제)',
    FOREIGN KEY (member_id) REFERENCES MEMBER(member_id)
)

DROP TABLE IF EXISTS SEARCH_STRUCTURE;
CREATE TABLE SEARCH_STRUCTURE (
    search_structure_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    search_id BIGINT,
    structure_id BIGINT,
    description TEXT,
    created_at DATETIME NOT NULL COMMENT '데이터 생성 시각',
    updated_at DATETIME NOT NULL COMMENT '데이터 수정 시각',
    is_deleted TINYINT(1) NOT NULL DEFAULT 0 COMMENT '삭제됨(0: 존재, 1: 삭제)',
    FOREIGN KEY (search_id) REFERENCES SEARCH(search_id),
    FOREIGN KEY (structure_id) REFERENCES STRUCTURE(structure_id)
);

DROP TABLE IF EXISTS SEARCH_STRUCTURE_NOTE;
CREATE TABLE SEARCH_STRUCTURE_NOTE (
    search_structure_note_id BIGINT AUTO_INCREMENT PRIMARY KEY, 
    search_structure_id BIGINT,
    note_field_id BIGINT,
    eval_type VARCHAR(100) COMMENT '평가타입',
    eval_value VARCHAR(100) COMMENT '평가값',
    note TEXT COMMENT '상세 메모',
    created_at DATETIME NOT NULL COMMENT '데이터 생성 시각',
    updated_at DATETIME NOT NULL COMMENT '데이터 수정 시각',
    is_deleted TINYINT(1) NOT NULL DEFAULT 0 COMMENT '삭제됨(0: 존재, 1: 삭제)',
    FOREIGN KEY (search_structure_id) REFERENCES SEARCH_STRUCTURE(search_structure_id),
    FOREIGN KEY (note_field_id) REFERENCES NOTE_FIELD(note_field_id)
);


