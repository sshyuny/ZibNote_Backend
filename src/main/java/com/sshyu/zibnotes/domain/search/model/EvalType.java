package com.sshyu.zibnotes.domain.search.model;

public enum EvalType {

    // 별점(1~5)
    STAR("STAR") {
        @Override
        public boolean hasProperValue(String evalValue) {
            Integer value = Integer.valueOf(evalValue);
            if (value >= 0 && value <= 5) { return true; }
            return false;
        }
    },
    // 점수(0~100)
    SCORE("SCORE") {
        @Override
        public boolean hasProperValue(String evalValue) {
            Integer value = Integer.valueOf(evalValue);
            if (value >= 0 && value <= 100) { return true; }
            return false;
        }
    },
    // 순위(1~N)
    RANK("RANK") {
        @Override
        public boolean hasProperValue(String evalValue) {
            Integer value = Integer.valueOf(evalValue);
            if (value <= 1) { return true; }
            return false;
        }
    },
    // 선택됨(1: true/0: false)
    CHECKED("CHECKED") {
        @Override
        public boolean hasProperValue(String evalValue) {
            Integer value = Integer.valueOf(evalValue);
            if (value == 1 || value == 0) { return true; }
            return false;
        }
    };

    private String name;
    
    private EvalType(final String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public abstract boolean hasProperValue(String evalValue);

}
