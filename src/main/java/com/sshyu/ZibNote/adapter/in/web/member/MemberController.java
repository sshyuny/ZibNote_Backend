package com.sshyu.zibnote.adapter.in.web.member;

import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sshyu.zibnote.adapter.in.web.common.res.ResponseCode;
import com.sshyu.zibnote.adapter.in.web.common.res.ApiResponse;
import com.sshyu.zibnote.adapter.in.web.common.res.ResponseMessage;
import com.sshyu.zibnote.adapter.in.web.member.dto.KakaoTokenResDto;
import com.sshyu.zibnote.adapter.in.web.member.dto.KakaoUserInfoResDto;
import com.sshyu.zibnote.adapter.in.web.member.dto.LoginReqDto;
import com.sshyu.zibnote.application.service.member.KakaoMemberService;
import com.sshyu.zibnote.domain.auth.model.Token;
import com.sshyu.zibnote.domain.auth.port.in.AuthUseCase;
import com.sshyu.zibnote.domain.member.exception.KakaoMemberException;
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
    private final KakaoMemberService kakaoLoginService;

    @Value("${auth.front.redirect-uri}")
    private String frontRedirectUri;

    @PostMapping("/pass/api/login")
    public ResponseEntity<ApiResponse<Void>> login(HttpSession httpSession, @RequestBody LoginReqDto loginReqDto) {

        final Token token = authUseCase.login(loginReqDto.getName());
        log.info("로그인 성공! " + loginReqDto.getName());

        return ResponseEntity
                    .ok()
                    .header("Authorization", "Bearer " + token.getToken())
                    .body(ApiResponse.withoutData(ResponseCode.SUCCESS, ResponseMessage.SUCCESS_LOGIN.getMessage()));
    }

    @GetMapping("/pass/oauth/kakao")
    public ResponseEntity<?> kakao(
        @RequestParam(value = "code", required = false) String code,
        @RequestParam(value = "error", required = false) String error,
        @RequestParam(value = "error_description", required = false) String error_description,
        @RequestParam(value = "state", required = false) String state
    ) {

        if (code != null) {
            KakaoTokenResDto kakaoToken = kakaoLoginService.getAccessToken(code);
            String accessToken = kakaoToken.getAccess_token();
            log.info("카카오 로그인 토큰 accessToken = {}", accessToken);

            KakaoUserInfoResDto kakaoUserInfo = kakaoLoginService.getUserInfo(accessToken);
            String id = kakaoUserInfo.getId();
            log.info("카카오 로그인 사용자 아이디 kakao user id = {}", id);
        } else {
            throw new KakaoMemberException("카카오 로그인 시도 실패 " + error);
        }

        HttpHeaders resHeader = new HttpHeaders();
        resHeader.setLocation(URI.create(frontRedirectUri));
        return new ResponseEntity<>(resHeader, HttpStatus.FOUND);
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
