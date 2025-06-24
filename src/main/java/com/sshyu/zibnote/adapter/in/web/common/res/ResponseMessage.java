package com.sshyu.zibnote.adapter.in.web.common.res;

public enum ResponseMessage {

    SUCCESS("정상 처리되었습니다."),
    SUCCESS_REGISTER("등록되었습니다."),
    SUCCESS_GET("조회되었습니다."),
    SUCCESS_DELETE("삭제되었습니다."),
    SUCCESS_UPDATE("변경되었습니다."),
    SUCCESS_LOGIN("로그인 성공"),
    SUCCESS_LOGOUT("로그아웃 성공"),

    ERROR("오류가 발생했습니다"),
    ERROR_NOT_FOUND("존재하지 않거나 접근할 수 없는 데이터입니다."),
    ERROR_INVALID("유효하지 않는 값입니다."),
    ERROR_ALREADY_DELETED("삭제된 데이터입니다.");

    private final String message;

    private ResponseMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

}
