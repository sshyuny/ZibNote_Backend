package com.sshyu.zibnote.domain.search.model;

public enum EvalType {

    STAR("STAR"),  // 별점(1~5)
    SCORE("SCORE"),  // 점수(0~100)
    RANK("RANK"),  // 순위(1~N)
    CHECKED("CHECKED");  // 선택됨(true/false)

    private String name;
    
    private EvalType(final String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

}
