package com.sshyu.zibnote.adapter.in.web.search;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sshyu.zibnote.adapter.in.web.common.res.ApiResponse;
import com.sshyu.zibnote.adapter.in.web.common.res.ResponseCode;
import com.sshyu.zibnote.adapter.in.web.common.res.ResponseMessage;
import com.sshyu.zibnote.adapter.in.web.search.dto.SearchStructurePutReqDto;
import com.sshyu.zibnote.adapter.in.web.search.dto.SearchStructureReqDto;
import com.sshyu.zibnote.adapter.in.web.search.dto.SearchStructureResDto;
import com.sshyu.zibnote.adapter.in.web.search.mapper.SearchStructureDtoMapper;
import com.sshyu.zibnote.domain.auth.port.in.AuthUseCase;
import com.sshyu.zibnote.domain.search.port.in.SearchStructureUseCase;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;

@RequestMapping("/api/search-structure")
@RestController
@RequiredArgsConstructor
public class SearchStructureController {
    
    private final SearchStructureUseCase searchStructureUseCase;
    private final AuthUseCase authUseCase;

    @Operation(
        summary = "SearchStructure 생성",
        description = "SearchStructure를 생성합니다."
    )
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "생성 성공"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content),
    })
    @PostMapping
    public ResponseEntity<ApiResponse<Void>> post(@RequestBody SearchStructureReqDto searchStructureReqDto) {

        final UUID loginedMemberId = authUseCase.getMemberId();
        searchStructureUseCase.registerSearchStructure(
            SearchStructureDtoMapper.toDomain(searchStructureReqDto), loginedMemberId
        );

        return ResponseEntity.ok(
            ApiResponse.withoutData(ResponseCode.SUCCESS, ResponseMessage.SUCCESS_REGISTER.getMessage())
        );
    }

    @Operation(
        summary = "SearchStructure 삭제",
        description = "SearchStructure를 삭제합니다."
    )
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "삭제 성공"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "존재하지 않거나 권한이 없는 리소스", content = @Content),
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(
        @Parameter(description = "삭제하려는 SearchStructure ID", example = "123e4567-e89b-12d3-a456-426614174000")
        @PathVariable("id") UUID id
    ) {

        final UUID loginedMemberId = authUseCase.getMemberId();
        searchStructureUseCase.softDeleteSearchStructure(id, loginedMemberId);

        return ResponseEntity.ok(
            ApiResponse.withoutData(ResponseCode.SUCCESS, ResponseMessage.SUCCESS_DELETE.getMessage())
        );
    }

    @Operation(
        summary = "SearchStructure 수정",
        description = "SearchStructure 필드를 수정합니다."
    )
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "수정 성공"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "존재하지 않거나 권한이 없는 리소스", content = @Content),
    })
    @PutMapping(consumes = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<ApiResponse<Void>> put(@RequestBody SearchStructurePutReqDto reqDto) {

        final UUID loginedMemberId = authUseCase.getMemberId();
        searchStructureUseCase.updateSearchStructure(SearchStructureDtoMapper.fromPutReqDtoToDomain(reqDto), loginedMemberId);

        return ResponseEntity.ok(
            ApiResponse.withoutData(ResponseCode.SUCCESS, ResponseMessage.SUCCESS_UPDATE.getMessage())
        );
    }

    @Operation(
        summary = "특정 Search와 연결되는 SearchStructure 리스트 조회",
        description = "특정 SearchStruc와 연결되는 SearchStructure 리스트를 조회합니다."
    )
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "조회 성공"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "존재하지 않거나 권한이 없는 리소스", content = @Content),
    })
    @GetMapping("/list")
    public ResponseEntity<ApiResponse<List<SearchStructureResDto>>> getList(
        @Parameter(description = "SearchStructure과 연결되는 것으로 조회하려는 Search ID", example = "123e4567-e89b-12d3-a456-426614174000")
        @RequestParam("searchId") UUID searchId
    ) {

        final UUID loginedMemberId = authUseCase.getMemberId();
        final List<SearchStructureResDto> searchStructureDtos = searchStructureUseCase.listSearchStructuresBySearch(searchId, loginedMemberId)
            .stream()
            .map(domain -> SearchStructureDtoMapper.toResDto(domain))
            .collect(Collectors.toList());

        return ResponseEntity.ok(
            ApiResponse.of(ResponseCode.SUCCESS, ResponseMessage.SUCCESS_GET.getMessage(), searchStructureDtos)
        );
    }

}
