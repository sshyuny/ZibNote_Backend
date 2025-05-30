package com.sshyu.zibnote.adapter.in.web.member;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sshyu.zibnote.adapter.in.web.common.res.ResponseCode;
import com.sshyu.zibnote.adapter.in.web.common.res.ApiResponse;
import com.sshyu.zibnote.adapter.in.web.common.res.ResponseMessage;
import com.sshyu.zibnote.adapter.in.web.member.dto.LoginReqDto;
import com.sshyu.zibnote.domain.auth.model.Token;
import com.sshyu.zibnote.domain.auth.port.in.AuthUseCase;
import com.sshyu.zibnote.domain.member.model.Member;
import com.sshyu.zibnote.domain.member.port.in.MemberUseCase;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
public class MemberController {

    private final MemberUseCase memberUseCase;
    private final AuthUseCase authUseCase;
    
    @PostMapping("/pass/api/login")
    public ResponseEntity<ApiResponse<Void>> login(HttpSession httpSession, @RequestBody LoginReqDto loginReqDto) {

        final Token token = authUseCase.login(loginReqDto.getName());
        log.info("로그인 성공! " + loginReqDto.getName());

        return ResponseEntity
                    .ok()
                    .header("Authorization", "Bearer " + token.getToken())
                    .body(ApiResponse.withoutData(ResponseCode.SUCCESS, ResponseMessage.SUCCESS_LOGIN.getMessage()));
    }

    @PostMapping("/api/logout")
    public ResponseEntity<ApiResponse<Void>> logout(@RequestBody LoginReqDto loginReqDto) {

        authUseCase.logout();
        return ResponseEntity.ok(
            ApiResponse.withoutData(ResponseCode.SUCCESS, ResponseMessage.SUCCESS_LOGOUT.getMessage())
        );
    }

    @PostMapping("/pass/api/register")
    public ResponseEntity<ApiResponse<Void>> post(@RequestBody LoginReqDto loginReqDto) {

        memberUseCase.register(Member.builder().name(loginReqDto.getName()).build());
        log.info("회원가입 성공! " + loginReqDto.getName());

        return ResponseEntity.ok(
            ApiResponse.withoutData(ResponseCode.SUCCESS, ResponseMessage.SUCCESS_REGISTER.getMessage())
        );
    }

    @GetMapping("/api/member")
    public ResponseEntity<ApiResponse<String>> get() {

        final String memberName = memberUseCase.getMember(authUseCase.getMemberId()).getName();
        return ResponseEntity.ok(
            ApiResponse.of(ResponseCode.SUCCESS, ResponseMessage.SUCCESS_REGISTER.getMessage(), memberName)
        );
    }

}
