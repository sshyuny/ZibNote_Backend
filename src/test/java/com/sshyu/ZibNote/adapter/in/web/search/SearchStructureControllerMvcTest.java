package com.sshyu.zibnote.adapter.in.web.search;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
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
import com.sshyu.zibnote.adapter.in.web.common.res.ResponseCode;
import com.sshyu.zibnote.adapter.in.web.common.res.ApiResponse;
import com.sshyu.zibnote.adapter.in.web.common.res.ResponseMessage;
import com.sshyu.zibnote.adapter.in.web.search.dto.SearchStructureReqDto;
import com.sshyu.zibnote.adapter.in.web.search.dto.SearchStructureResDto;
import com.sshyu.zibnote.application.service.auth.JwtUtil;
import com.sshyu.zibnote.domain.auth.port.in.AuthUseCase;
import com.sshyu.zibnote.domain.common.exception.UnauthorizedAccessException;
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

    ObjectMapper objectMapper = new ObjectMapper();

    final static String PATH = "/api/search-structure";
    final static String JWT_TOKEN = "dummy.jwt.token";
    final static Long LOGINED_MEMBER_ID = 2345L;

    final static UUID SEARCH_STRUCTURE_ID = UUID.randomUUID();


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
        String json = objectMapper.writeValueAsString(
            new SearchStructureReqDto(22L, 33L, null)
        );
        
        // when
        MvcResult res = mockMvc.perform(post(PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + JWT_TOKEN)
            )
            .andExpect(status().isOk())
            .andReturn();
        ApiResponse<Void> value = objectMapper.readValue(
            res.getResponse().getContentAsString(), new TypeReference<ApiResponse<Void>>() {}
        );

        // then
        assertThat(value.getMessage()).isEqualTo(ResponseMessage.SUCCESS_REGISTER.getMessage());
        assertThat(value.getCode()).isEqualTo(ResponseCode.SUCCESS);
    }

    @Test
    void delete_정상_삭제() throws Exception {
        //when
        MvcResult res = mockMvc.perform(delete(PATH + "/" + SEARCH_STRUCTURE_ID)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + JWT_TOKEN)
            )
            .andExpect(status().isOk())
            .andReturn();
        ApiResponse<Void> resBody = objectMapper.readValue(
            res.getResponse().getContentAsString(), new TypeReference<ApiResponse<Void>>() {}
        );

        //then
        assertThat(resBody.getCode()).isEqualTo(ResponseCode.SUCCESS);
        assertThat(resBody.getMessage()).isEqualTo(ResponseMessage.SUCCESS_DELETE.getMessage());
    }

    @Test
    void delete_비인가_사용자_시도시_예외_발생() throws Exception {
        //given
        doThrow(new UnauthorizedAccessException())
            .when(searchStructureUseCase)
            .softDeleteSearchStructure(SEARCH_STRUCTURE_ID, LOGINED_MEMBER_ID);

        //when
        MvcResult res = mockMvc.perform(delete(PATH + "/" + SEARCH_STRUCTURE_ID)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + JWT_TOKEN)
            )
            .andExpect(status().isBadRequest())
            .andReturn();
        ApiResponse<Void> resBody = objectMapper.readValue(res.getResponse().getContentAsString(), 
            new TypeReference<ApiResponse<Void>>() {}
        );

        //then
        assertThat(resBody.getCode()).isEqualTo(ResponseCode.ERROR);
        assertThat(resBody.getMessage()).isEqualTo(ResponseMessage.ERROR_UNAUTHORIZED.getMessage());
    }

    @Test
    void get_list_정상_요청() throws Exception {
        //given
        Long searchId = 44554455L;
        given(searchStructureUseCase.listSearchStructuresBySearch(searchId, LOGINED_MEMBER_ID))
            .willReturn(List.of());
        
        //when
        MvcResult res = mockMvc.perform(get(PATH + "/list?searchId=" + searchId)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + JWT_TOKEN)
            )
            .andExpect(status().isOk())
            .andReturn();
        ApiResponse<List<SearchStructureResDto>> resBody = objectMapper.readValue(res.getResponse().getContentAsString(), 
            new TypeReference<ApiResponse<List<SearchStructureResDto>>>() {}
        );

        //then
        assertThat(resBody.getCode()).isEqualTo(ResponseCode.SUCCESS);
        assertThat(resBody.getData()).hasSize(0);
    }

}
