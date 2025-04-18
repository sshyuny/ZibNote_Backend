package com.sshyu.zibnote.adapter.in.web.member;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sshyu.zibnote.adapter.in.web.common.ResBodyForm;
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
    public ResBodyForm login(HttpSession httpSession, @RequestBody LoginReqDto loginReqDto) {

        authUseCase.login(loginReqDto.getName());
        log.info("로그인 성공! " + loginReqDto.getName());

        return ResBodyForm.builder().data("success").build();
    }

    @PostMapping("/logout")
    public ResBodyForm logout(@RequestBody LoginReqDto loginReqDto) {

        authUseCase.logout();
        return ResBodyForm.builder().data("success").build();
    }

    @PostMapping("/register")
    public ResBodyForm post(@RequestBody LoginReqDto loginReqDto) {

        memberUseCase.register(Member.builder().name(loginReqDto.getName()).build());
        log.info("회원가입 성공! " + loginReqDto.getName());

        return ResBodyForm.builder().data("success").build();
    }

    @GetMapping
    public ResBodyForm get() {

        String memberName = authUseCase.getSessionMember().getName();
        return ResBodyForm.builder().data(memberName).build();
    }

}
