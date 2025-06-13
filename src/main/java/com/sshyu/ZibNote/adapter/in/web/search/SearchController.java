package com.sshyu.zibnote.adapter.in.web.search;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sshyu.zibnote.adapter.in.web.common.res.ResponseCode;
import com.sshyu.zibnote.adapter.in.web.common.res.ApiResponse;
import com.sshyu.zibnote.adapter.in.web.common.res.ResponseMessage;
import com.sshyu.zibnote.adapter.in.web.search.dto.SearchPutReqDto;
import com.sshyu.zibnote.adapter.in.web.search.dto.SearchReqDto;
import com.sshyu.zibnote.adapter.in.web.search.dto.SearchResDto;
import com.sshyu.zibnote.adapter.in.web.search.mapper.SearchDtoMapper;
import com.sshyu.zibnote.domain.auth.port.in.AuthUseCase;
import com.sshyu.zibnote.domain.search.port.in.SearchUseCase;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/search")
@RestController
public class SearchController {

    private final SearchUseCase searchUseCase;
    private final AuthUseCase authUseCase;
    
    @Operation(
        summary = "Search 생성",
        description = "Search를 생성합니다."
    )
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "생성 성공"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content),
    })
    @PostMapping
    public ResponseEntity<ApiResponse<Void>> post(@RequestBody SearchReqDto reqDto) {

        final UUID memberId = authUseCase.getMemberId();
        searchUseCase.registerSearch(SearchDtoMapper.toDomain(reqDto, memberId));

        return ResponseEntity.ok(
            ApiResponse.withoutData(ResponseCode.SUCCESS, ResponseMessage.SUCCESS_REGISTER.getMessage())
        );
    }

    @Operation(
        summary = "Search 삭제",
        description = "Search를 삭제합니다."
    )
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "삭제 성공"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "존재하지 않거나 권한이 없는 리소스", content = @Content),
    })
    @DeleteMapping
    public ResponseEntity<ApiResponse<Void>> delete(
        @Parameter(description = "삭제하려는 Search ID", example = "123e4567-e89b-12d3-a456-426614174000")
        @PathVariable("id") UUID id
    ) {

        searchUseCase.softDeleteSearch(id, authUseCase.getMemberId());

        return ResponseEntity.ok(
            ApiResponse.withoutData(ResponseCode.SUCCESS, ResponseMessage.SUCCESS_DELETE.getMessage())
        );
    }

    @Operation(
        summary = "Search 수정",
        description = "Search 필드를 수정합니다."
    )
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "수정 성공"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "존재하지 않거나 권한이 없는 리소스", content = @Content),
    })
    @PutMapping
    public ResponseEntity<ApiResponse<Void>> put(@RequestBody SearchPutReqDto reqDto) {

        searchUseCase.updateSearch(
            SearchDtoMapper.fromPutReqDtoToDomain(reqDto), 
            authUseCase.getMemberId()
        );

        return ResponseEntity.ok(
            ApiResponse.withoutData(ResponseCode.SUCCESS, ResponseMessage.SUCCESS_UPDATE.getMessage())
        );
    }

    @Operation(
        summary = "로그인한 사용자가 등록한 Search 리스트 조회",
        description = "로그인한 사용자가 등록한 Search  리스트를 조회합니다."
    )
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "조회 성공"),
    })
    @GetMapping("/list")
    public ResponseEntity<ApiResponse<List<SearchResDto>>> getList() {

        final List<SearchResDto> resDtos = searchUseCase.listSearchesByMember(authUseCase.getMemberId())
            .stream()
            .map(domain -> SearchDtoMapper.toResDto(domain))
            .collect(Collectors.toList());

        return ResponseEntity.ok(
            ApiResponse.of(ResponseCode.SUCCESS, ResponseMessage.SUCCESS_GET.getMessage(), resDtos)
        );
    }

}
