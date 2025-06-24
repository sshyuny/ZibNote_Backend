package com.sshyu.zibnote.application.service.member;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.sshyu.zibnote.adapter.in.web.member.dto.KakaoTokenResDto;
import com.sshyu.zibnote.adapter.in.web.member.dto.KakaoUserInfoResDto;

@Service
public class KakaoMemberService {

    private String kakaoTokenRequestUrl = "https://kauth.kakao.com/oauth/token";
    private String kakaoUserInfoRequestUrl = "https://kapi.kakao.com/v2/user/me";

    private String kakaoGrantType = "authorization_code";
    @Value("${auth.kakao.client_id}")
    private String kakaoClientId;
    @Value("${auth.kakao.redirect_uri}")
    private String kakaoRedirectUri;
    
    public KakaoTokenResDto getAccessToken(String code) {

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", kakaoGrantType);
        params.add("client_id", kakaoClientId);
        params.add("redirect_uri", kakaoRedirectUri);
        params.add("code", code);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        ResponseEntity<KakaoTokenResDto> response = restTemplate.postForEntity(
            kakaoTokenRequestUrl,
            request,
            KakaoTokenResDto.class
        );

        return response.getBody();
    }

    public KakaoUserInfoResDto getUserInfo(String accessToken) {

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<KakaoUserInfoResDto> response = restTemplate.exchange(
            kakaoUserInfoRequestUrl,
            HttpMethod.GET,
            entity,
            KakaoUserInfoResDto.class
        );

        return response.getBody();
    }

}
