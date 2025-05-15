package com.sshyu.zibnote.adapter.in.web.structure;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sshyu.zibnote.adapter.in.web.common.ApiResponse;
import com.sshyu.zibnote.adapter.in.web.structure.dto.StructureResDto;
import com.sshyu.zibnote.application.service.auth.JwtUtil;
import com.sshyu.zibnote.domain.structure.model.Structure;
import com.sshyu.zibnote.domain.structure.port.in.StructureUseCase;


@WebMvcTest(StructureController.class)
public class StructureControllerTestMvc {

    @Autowired
    MockMvc mockMvc;
    @MockitoBean
    StructureUseCase structureUseCase;
    @MockitoBean
    JwtUtil jwtUtil;

    ObjectMapper objectMapper = new ObjectMapper();

    // web
    final static String JWT_TOKEN = "dummy.jwt.token";
    // data value
    final static Long STRUCTURE_ID_1 = 23L;
    final static String STRUCTURE_NAME_1 = "목화아파트";
    final static String NUMBER_ADDRESS_1 = "경기 군포시 금정동 850";
    final static String ROAD_ADDRESS_1 = "경기 군포시 번영로550번길 5";
    final static Long STRUCTURE_ID_2 = 49L;
    final static String STRUCTURE_NAME_2 = "대림솔거아파트";
    final static String NUMBER_ADDRESS_2 = "경기 군포시 산본동 1146";
    final static String ROAD_ADDRESS_2 = "경기 군포시 광정로 119";
    Structure structure1 = Structure.builder()
        .structureId(STRUCTURE_ID_1)
        .name(STRUCTURE_NAME_1)
        .numberAddress(NUMBER_ADDRESS_1)
        .roadAddress(ROAD_ADDRESS_1)
        .build();
    Structure structure2 = Structure.builder()
        .structureId(STRUCTURE_ID_2)
        .name(STRUCTURE_NAME_2)
        .numberAddress(NUMBER_ADDRESS_2)
        .roadAddress(ROAD_ADDRESS_2)
        .build();
    MockHttpSession session = new MockHttpSession();


    @BeforeEach
    void beforeEach() {
        given(jwtUtil.validateToken(JWT_TOKEN)).willReturn(true);
    }

    @Test
    void get_list_주소검색_정상요청() throws Exception {

        // given
        List<Structure> structures = new ArrayList<>();
        structures.add(structure1);
        structures.add(structure2);

        given(structureUseCase.listStructuresByAddress("경기"))
            .willReturn(structures);

        // when
        MvcResult response = mockMvc.perform(get("/api/structure/list?address=경기")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + JWT_TOKEN)
            )
            .andExpect(status().isOk())
            .andReturn();

        String contentAsString = response.getResponse().getContentAsString();
        ApiResponse<List<StructureResDto>> value = objectMapper.readValue(
            contentAsString, new TypeReference<ApiResponse<List<StructureResDto>>>() {}
        );

        // then
        assertThat(value.getData().size()).isEqualTo(2);
        assertThat(value.getData().get(0).getStructureId()).isEqualTo(STRUCTURE_ID_1);
        assertThat(value.getData().get(1).getName()).isEqualTo(STRUCTURE_NAME_2);
    }
    
}
