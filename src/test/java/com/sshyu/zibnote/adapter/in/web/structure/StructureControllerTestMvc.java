package com.sshyu.zibnote.adapter.in.web.structure;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sshyu.zibnote.adapter.in.web.common.res.ApiResponse;
import com.sshyu.zibnote.adapter.in.web.structure.StructureController;
import com.sshyu.zibnote.adapter.in.web.structure.dto.StructureResDto;
import com.sshyu.zibnote.application.service.auth.JwtUtil;
import com.sshyu.zibnote.domain.structure.model.Structure;
import com.sshyu.zibnote.domain.structure.port.in.StructureUseCase;
import com.sshyu.zibnote.fixture.StructureFixture;


@WebMvcTest(StructureController.class)
public class StructureControllerTestMvc {

    @Autowired
    MockMvc mockMvc;
    @MockitoBean
    StructureUseCase structureUseCase;
    @MockitoBean
    JwtUtil jwtUtil;

    ObjectMapper objectMapper = new ObjectMapper();

    final static String JWT_TOKEN = "dummy.jwt.token";
    final static String PATH = "/api/structure";

    final static Long STRUCTURE_1_ID = StructureFixture.STRUCTURE_1_ID;
    final static Long STRUCTURE_2_ID = StructureFixture.STRUCTURE_2_ID;
    final static String STRUCTURE_1_NAME = StructureFixture.STRUCTURE_1_NAME;
    final static String STRUCTURE_2_NAME = StructureFixture.STRUCTURE_2_NAME;
    Structure structure1 = StructureFixture.validStructure1();
    Structure structure2 = StructureFixture.validStructure2();
    List<Structure> structures = List.of(structure1, structure2);


    @BeforeEach
    void beforeEach() {
        given(jwtUtil.validateToken(JWT_TOKEN)).willReturn(true);
    }

    @Test
    void get_list_address_파라미터_요청() throws Exception {
        // given
        String urlParam = "경기";
        given(structureUseCase.listStructuresByAddress(urlParam))
            .willReturn(structures);

        // when
        MvcResult response = mockMvc.perform(get(PATH + "/list?address=" + urlParam)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + JWT_TOKEN)
            )
            .andExpect(status().isOk())
            .andReturn();
        ApiResponse<List<StructureResDto>> value = objectMapper.readValue(
            response.getResponse().getContentAsString(), new TypeReference<ApiResponse<List<StructureResDto>>>() {}
        );

        // then
        assertThat(value.getData().size()).isEqualTo(2);
    }

    @Test
    void get_list_name_파라미터_요청() throws Exception {
        // given
        String urlParam = "아파트";
        given(structureUseCase.listStructuresByName(urlParam))
            .willReturn(structures);

        // when
        MvcResult response = mockMvc.perform(get(PATH + "/list?name=" + urlParam)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + JWT_TOKEN)
            )
            .andExpect(status().isOk())
            .andReturn();
        ApiResponse<List<StructureResDto>> value = objectMapper.readValue(
            response.getResponse().getContentAsString(), new TypeReference<ApiResponse<List<StructureResDto>>>() {}
        );

        // then
        assertThat(value.getData().size()).isEqualTo(2);
    }
    
}
