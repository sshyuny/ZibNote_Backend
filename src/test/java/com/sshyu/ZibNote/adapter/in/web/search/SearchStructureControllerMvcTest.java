package com.sshyu.zibnote.adapter.in.web.search;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sshyu.zibnote.adapter.in.web.common.res.ResponseCode;
import com.sshyu.zibnote.adapter.in.web.common.res.ApiResponse;
import com.sshyu.zibnote.adapter.in.web.common.res.ResponseMessage;
import com.sshyu.zibnote.adapter.in.web.search.dto.SearchStructureReqDto;
import com.sshyu.zibnote.application.service.auth.JwtUtil;
import com.sshyu.zibnote.domain.auth.port.in.AuthUseCase;
import com.sshyu.zibnote.domain.search.port.in.SearchStructureUseCase;

@WebMvcTest(SearchStructureController.class)
public class SearchStructureControllerMvcTest {
    
    @Autowired
    MockMvc mockMvc;
    @MockitoBean
    SearchStructureUseCase searchStructureUseCase;
    @MockitoBean
    AuthUseCase authUseCase;
    @MockitoBean
    JwtUtil jwtUtil;

    MockHttpSession session = new MockHttpSession();
    ObjectMapper objectMapper = new ObjectMapper();

    // web
    final static String PATH = "/api/search-structure";
    final static String JWT_TOKEN = "dummy.jwt.token";
    // data value
    final static Long LOGINED_MEMBER_ID = 2345L;


    @BeforeEach
    void beforeEach() {

        given(authUseCase.getMemberId())
            .willReturn(LOGINED_MEMBER_ID);
        given(jwtUtil.validateToken(JWT_TOKEN))
            .willReturn(true);
    }

    @Test
    void post_정상요청() throws Exception {

        // given
        SearchStructureReqDto reqDto = new SearchStructureReqDto(
            22L, 33L, "test"
        );

        String json = objectMapper.writeValueAsString(reqDto);
        
        // when
        MvcResult response = mockMvc.perform(post(PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + JWT_TOKEN)
            )
            .andExpect(status().isOk())
            .andReturn();
        String contentAsString = response.getResponse().getContentAsString();
        ApiResponse<Void> value = objectMapper.readValue(contentAsString, new TypeReference<ApiResponse<Void>>() {});

        // then
        assertThat(value.getMessage()).isEqualTo(ResponseMessage.SUCCESS_REGISTER.getMessage());
        assertThat(value.getCode()).isEqualTo(ResponseCode.SUCCESS);
    }
}
