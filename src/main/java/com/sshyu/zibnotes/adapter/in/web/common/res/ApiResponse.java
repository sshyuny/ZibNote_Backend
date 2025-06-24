package com.sshyu.zibnotes.adapter.in.web.common.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder @Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    
    private ResponseCode code;
    private String message;
    private T data;

    public static <T> ApiResponse<T> of(final ResponseCode code, final String message, final T data) {
        return ApiResponse.<T>builder()
                .code(code)
                .message(message)
                .data(data)
                .build();
    }

    public static <T> ApiResponse<T> withoutData(final ResponseCode code, final String message) {
        return ApiResponse.<T>builder()
                .code(code)
                .message(message)
                .build();
    }

}
