package com.sshyu.zibnote.adapter.in.web.member;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sshyu.zibnote.adapter.in.web.common.ApiResponse;
import com.sshyu.zibnote.adapter.in.web.member.dto.LoginReqDto;
import com.sshyu.zibnote.domain.auth.port.in.AuthUseCase;
import com.sshyu.zibnote.domain.member.model.Member;
import com.sshyu.zibnote.domain.member.port.in.MemberUseCase;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/member")
@RestController
public class MemberController {

    private final MemberUseCase memberUseCase;
    private final AuthUseCase authUseCase;
    
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<Void>> login(HttpSession httpSession, @RequestBody LoginReqDto loginReqDto) {

        authUseCase.login(loginReqDto.getName());
        log.info("로그인 성공! " + loginReqDto.getName());

        return ResponseEntity.ok(ApiResponse.codeAndMessage("login success", "로그인 성공!"));
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(@RequestBody LoginReqDto loginReqDto) {

        authUseCase.logout();
        return ResponseEntity.ok(ApiResponse.codeAndMessage("logout success", "로그아웃 성공!"));
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<String>> post(@RequestBody LoginReqDto loginReqDto) {

        memberUseCase.register(Member.builder().name(loginReqDto.getName()).build());
        log.info("회원가입 성공! " + loginReqDto.getName());

        return ResponseEntity.ok(ApiResponse.successWithDataAndMessage(loginReqDto.getName(), "회원가입 성공!"));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<String>> get() {

        String memberName = authUseCase.getSessionMember().getName();
        return ResponseEntity.ok(ApiResponse.successWithData(memberName));
    }

}
