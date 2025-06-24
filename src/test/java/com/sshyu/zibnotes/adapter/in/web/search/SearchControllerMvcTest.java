package com.sshyu.zibnotes.adapter.in.web.search;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sshyu.zibnotes.adapter.in.web.common.res.ApiResponse;
import com.sshyu.zibnotes.adapter.in.web.common.res.ResponseCode;
import com.sshyu.zibnotes.adapter.in.web.common.res.ResponseMessage;
import com.sshyu.zibnotes.adapter.in.web.search.dto.SearchPutReqDto;
import com.sshyu.zibnotes.application.service.auth.JwtUtil;
import com.sshyu.zibnotes.domain.auth.port.in.AuthUseCase;
import com.sshyu.zibnotes.domain.common.exception.UnauthorizedAccessException;
import com.sshyu.zibnotes.domain.search.port.in.SearchUseCase;
import com.sshyu.zibnotes.adapter.in.web.search.SearchController;

@WebMvcTest(SearchController.class)
public class SearchControllerMvcTest {

    @Autowired
    MockMvc mockMvc;
    @MockitoBean
    SearchUseCase searchUseCase;
    @MockitoBean
    AuthUseCase authUseCase;
    @MockitoBean
    JwtUtil jwtUtil;

    ObjectMapper objectMapper = new ObjectMapper();

    final static String PATH = "/api/search";
    final static String JWT_TOKEN = "dummy.jwt.token";
    final static UUID LOGINED_MEMBER_ID = UUID.randomUUID();

    final static UUID SEARCH_ID = UUID.randomUUID();

    @BeforeEach
    void beforeEach() {

        given(authUseCase.getMemberId())
            .willReturn(LOGINED_MEMBER_ID);
        given(jwtUtil.validateToken(JWT_TOKEN))
            .willReturn(true);
    }
    
    @Test
    void update_정상_요청() throws Exception {
        //given
        String json = objectMapper.writeValueAsString(
            new SearchPutReqDto(SEARCH_ID, "산본역 2025 개인 임장", "경기 군포시", "")
        );

        //when
        MvcResult res = mockMvc.perform(put(PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + JWT_TOKEN)
            )
            .andExpect(status().isOk())
            .andReturn();
        ApiResponse<Void> resBody = objectMapper.readValue(
            res.getResponse().getContentAsString(), new TypeReference<ApiResponse<Void>>() {}
        );

        //then
        assertThat(resBody.getCode()).isEqualTo(ResponseCode.SUCCESS);
    }

    @Test
    void update_비인가_사용자_시도시_예외_발생() throws Exception {
        //given
        String json = objectMapper.writeValueAsString(
            new SearchPutReqDto(SEARCH_ID, "산본역 2025 개인 임장", "경기 군포시", "")
        );
        given(authUseCase.getMemberId())
            .willReturn(LOGINED_MEMBER_ID);
        doThrow(new UnauthorizedAccessException())
            .when(searchUseCase)
            .updateSearch(any(), eq(LOGINED_MEMBER_ID));

        //when
        MvcResult res = mockMvc.perform(put(PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + JWT_TOKEN)
            )
            .andExpect(status().isNotFound())
            .andReturn();
        ApiResponse<Void> resBody = objectMapper.readValue(
            res.getResponse().getContentAsString(), new TypeReference<ApiResponse<Void>>() {}
        );

        //then
        assertThat(resBody.getCode()).isEqualTo(ResponseCode.ERROR);
        assertThat(resBody.getMessage()).isEqualTo(ResponseMessage.ERROR_NOT_FOUND.getMessage());
    }


}
