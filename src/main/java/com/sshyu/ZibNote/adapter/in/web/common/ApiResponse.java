package com.sshyu.zibnote.adapter.in.web.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder @Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    
    private String code;
    private String message;
    private T data;

    public static <T> ApiResponse<T> successWithDataAndMessage(T data, String message) {
        return ApiResponse.<T>builder()
                .code("success")
                .data(data)
                .message(message)
                .build();
    }

    public static <T> ApiResponse<T> successWithData(T data) {
        return ApiResponse.<T>builder()
                .code("success")
                .data(data)
                .build();
    }

    public static ApiResponse<Void> successWithMessage(String message) {
        return ApiResponse.<Void>builder()
                .code("success")
                .data(null)
                .message(message)
                .build();
    }

    public static ApiResponse<Void> codeAndMessage(String code, String message) {
        return ApiResponse.<Void>builder()
                .code(code)
                .message(message)
                .data(null)
                .build();
    }

    public static ApiResponse<Void> onlyCode(String code) {
        return ApiResponse.<Void>builder()
                .code(code)
                .data(null)
                .build();
    }

}
