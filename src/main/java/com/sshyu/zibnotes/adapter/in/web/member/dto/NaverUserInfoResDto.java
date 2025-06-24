package com.sshyu.zibnotes.adapter.in.web.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
public class NaverUserInfoResDto {
    
    private String resultcode;
    private String message;
    private NaverUserInfoResponse response;

    @NoArgsConstructor @AllArgsConstructor
    @Getter @Setter
    public class NaverUserInfoResponse {
        private String id;
        private String nickname;
        private String name;
        private String email;
    }

}
