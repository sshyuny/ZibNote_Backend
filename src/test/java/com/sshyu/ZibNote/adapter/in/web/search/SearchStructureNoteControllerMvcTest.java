package com.sshyu.zibnote.adapter.in.web.search;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
import com.sshyu.zibnote.adapter.in.web.common.res.ApiResponse;
import com.sshyu.zibnote.adapter.in.web.common.res.ResponseCode;
import com.sshyu.zibnote.adapter.in.web.common.res.ResponseMessage;
import com.sshyu.zibnote.adapter.in.web.search.dto.NotePostReqDto;
import com.sshyu.zibnote.adapter.in.web.search.dto.NoteResDto;
import com.sshyu.zibnote.application.service.auth.JwtUtil;
import com.sshyu.zibnote.domain.auth.port.in.AuthUseCase;
import com.sshyu.zibnote.domain.common.exception.UnauthorizedAccessException;
import com.sshyu.zibnote.domain.search.port.in.SearchStructureNoteUseCase;

@WebMvcTest(SearchStructureNoteController.class)
public class SearchStructureNoteControllerMvcTest {
    
    @Autowired
    MockMvc mockMvc;
    @MockitoBean
    SearchStructureNoteUseCase searchStructureNoteUseCase;
    @MockitoBean
    AuthUseCase authUseCase;
    @MockitoBean
    JwtUtil jwtUtil;

    ObjectMapper objectMapper = new ObjectMapper();

    final static String PATH = "/api/search-structure-note";
    final static String JWT_TOKEN = "dummy.jwt.token";
    final static UUID LOGINED_MEMBER_ID = UUID.randomUUID();
    
    final static UUID SEARCH_STRUCTURE_NOTE_ID = UUID.randomUUID();
    final static UUID SEARCH_STRUCTURE_ID = UUID.randomUUID();
    final static Long NOTE_FIELD_ID = 333L;


    @BeforeEach
    void beforeEach() {

        given(authUseCase.getMemberId())
            .willReturn(LOGINED_MEMBER_ID);
        given(jwtUtil.validateToken(JWT_TOKEN))
            .willReturn(true);
    }

    @Test
    void post_정상_요청() throws Exception {
        // given
        String json = objectMapper.writeValueAsString(
            new NotePostReqDto(SEARCH_STRUCTURE_ID, NOTE_FIELD_ID, null, null, null)
        );

        // when
        MvcResult res = mockMvc.perform(post(PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + JWT_TOKEN)
            )
            .andExpect(status().isOk())
            .andReturn();
        ApiResponse<Void> resBody = objectMapper.readValue(
            res.getResponse().getContentAsString(), new TypeReference<ApiResponse<Void>>() {}
        );

        // then
        assertThat(resBody.getCode()).isEqualTo(ResponseCode.SUCCESS);
        assertThat(resBody.getMessage()).isEqualTo(ResponseMessage.SUCCESS_REGISTER.getMessage());
    }

    @Test
    void post_유효하지_않은_요청_데이터() throws Exception {
        // given
        String json = """
                "searchStructureId": 222L,
            """;

        // when
        MvcResult res = mockMvc.perform(post(PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + JWT_TOKEN)
            )
            .andExpect(status().isInternalServerError())
            .andReturn();
        ApiResponse<Void> resBody = objectMapper.readValue(
            res.getResponse().getContentAsString(), new TypeReference<ApiResponse<Void>>() {}
        );

        // then
        assertThat(resBody.getCode()).isEqualTo(ResponseCode.ERROR);
        assertThat(resBody.getMessage()).isEqualTo(ResponseMessage.ERROR.getMessage());
    }

    @Test
    void delete_정상_요청() throws Exception {
        // when
        MvcResult res = mockMvc.perform(delete(PATH + "/" + SEARCH_STRUCTURE_NOTE_ID)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + JWT_TOKEN)
            )
            .andExpect(status().isOk())
            .andReturn();
        ApiResponse<Void> resBody = objectMapper.readValue(
            res.getResponse().getContentAsString(), new TypeReference<ApiResponse<Void>>() {}
        );

        // then
        assertThat(resBody.getCode()).isEqualTo(ResponseCode.SUCCESS);
        assertThat(resBody.getMessage()).isEqualTo(ResponseMessage.SUCCESS_DELETE.getMessage());
    }

    @Test
    void delete_비인가_사용자_시도시_예외_발생() throws Exception {
        // given
        doThrow(new UnauthorizedAccessException())
            .when(searchStructureNoteUseCase)
            .softDeleteSearchStructureNote(SEARCH_STRUCTURE_NOTE_ID, LOGINED_MEMBER_ID);

        // when
        MvcResult res = mockMvc.perform(delete(PATH + "/" + SEARCH_STRUCTURE_NOTE_ID)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + JWT_TOKEN)
            )
            .andExpect(status().isBadRequest())
            .andReturn();
        ApiResponse<Void> resBody = objectMapper.readValue(
            res.getResponse().getContentAsString(), new TypeReference<ApiResponse<Void>>() {}
        );

        // then
        assertThat(resBody.getCode()).isEqualTo(ResponseCode.ERROR);
        assertThat(resBody.getMessage()).isEqualTo(ResponseMessage.ERROR_UNAUTHORIZED.getMessage());
    }

    @Test
    void get_리스트_정상_조회() throws Exception {
        //given
        given(searchStructureNoteUseCase.listSearchStructureNotesBySearchStructure(SEARCH_STRUCTURE_ID, LOGINED_MEMBER_ID))
            .willReturn(List.of());

        //when
        MvcResult res = mockMvc.perform(get(PATH + "/list?searchStructureId=" + SEARCH_STRUCTURE_ID)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + JWT_TOKEN)
            )
            .andExpect(status().isOk())
            .andReturn();
        ApiResponse<List<NoteResDto>> resBody = objectMapper.readValue(res.getResponse().getContentAsString(), 
            new TypeReference<ApiResponse<List<NoteResDto>>>() {}
        );

        // then
        assertThat(resBody.getData()).hasSize(0);
        assertThat(resBody.getCode()).isEqualTo(ResponseCode.SUCCESS);
        assertThat(resBody.getMessage()).isEqualTo(ResponseMessage.SUCCESS_GET.getMessage());
    }

}
