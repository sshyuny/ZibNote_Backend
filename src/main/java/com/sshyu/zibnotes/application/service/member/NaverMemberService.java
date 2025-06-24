package com.sshyu.zibnotes.application.service.member;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.sshyu.zibnotes.adapter.in.web.member.dto.NaverTokenResDto;
import com.sshyu.zibnotes.adapter.in.web.member.dto.NaverUserInfoResDto;
import com.sshyu.zibnotes.domain.member.exception.SocialLoginException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class NaverMemberService {

    private String naverTokenRequestUrl = "https://nid.naver.com/oauth2.0/token";
    private String naverUserInfoRequestUrl = "https://openapi.naver.com/v1/nid/me";

    private String grantType = "authorization_code";
    @Value("${auth.naver.client_id}")
    private String naverClientId;
    @Value("${auth.naver.redirect_uri}")
    private String naverRedirectUri;
    @Value("${auth.naver.client_secret}")
    private String naverClientSecret;
    @Value("${auth.naver.state}")
    private String naverState;
    
    public void doSocialLogin(final String code, final String error, final String error_description, final String state) {

        log.info("[Naver Social Login] Callback Info: code={}, error={}, error_description={}, state={}", code, error, error_description, state);

        if (code == null) {
            throw new SocialLoginException("[Naver Social Login] 네이버 로그인 인증 code 받는 중 예외 발생");
        }

        NaverTokenResDto naverAccessToken = requestAccessToken(code);
        requestUserInfo(naverAccessToken.getAccess_token());
    }

    public NaverTokenResDto requestAccessToken(String code) {

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();

        HttpEntity<Void> entity = new HttpEntity<>(headers);
        String url = naverTokenRequestUrl + "?" +
                        "grant_type=" + grantType +
                        "&client_id=" + naverClientId +
                        "&client_secret=" + naverClientSecret +
                        "&code=" + code +
                        "&state" + naverState;
        ResponseEntity<NaverTokenResDto> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                NaverTokenResDto.class
            );
        NaverTokenResDto resBody = response.getBody();

        if (resBody == null) {
            throw new SocialLoginException("[Naver Social Login] Access Token 요청 후 응답 바디 null");
        }

        log.info(
            "[Naver Social Login] Response of Access Token: access_token={}, refresh_token={}, error={}, error_description={}",
            resBody.getAccess_token(),
            resBody.getRefresh_token(),
            resBody.getError(),
            resBody.getError_description()
        );

        return resBody;
    }

    public NaverUserInfoResDto requestUserInfo(String accessToken) {

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        NaverUserInfoResDto resBody;
        try {
            ResponseEntity<NaverUserInfoResDto> response = restTemplate.exchange(
                naverUserInfoRequestUrl,
                HttpMethod.GET,
                entity,
                NaverUserInfoResDto.class
            );
            resBody = response.getBody();
        } catch (RestClientException e) {
            throw new SocialLoginException("[Naver Social Login] user info 요청 중 예외 발생");
        }

        if (resBody == null) {
            throw new SocialLoginException("[Naver Social Login] user info 요청 후 응답 바디 null");
        }

        log.info(
            "Response of User Info: resultcode={}, message={}, response/id={}, response/name={}",
            resBody.getResultcode(),
            resBody.getMessage(),
            resBody.getResponse().getId(),
            resBody.getResponse().getName()
        );

        return resBody;
    }

}
