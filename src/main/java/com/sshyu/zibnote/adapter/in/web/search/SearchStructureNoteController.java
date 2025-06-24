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
import com.sshyu.zibnote.adapter.in.web.search.dto.NotePostReqDto;
import com.sshyu.zibnote.adapter.in.web.search.dto.NotePutReqDto;
import com.sshyu.zibnote.adapter.in.web.search.dto.NoteResDto;
import com.sshyu.zibnote.adapter.in.web.search.mapper.SearchStructureNoteDtoMapper;
import com.sshyu.zibnote.domain.auth.port.in.AuthUseCase;
import com.sshyu.zibnote.domain.search.port.in.SearchStructureNoteUseCase;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "SearchStructureNote", description = "the SearchStructureNote API")
@RequestMapping("/api/search-structure-note")
@RequiredArgsConstructor
@RestController
public class SearchStructureNoteController {

    private final SearchStructureNoteUseCase searchStructureNoteUseCase;
    private final AuthUseCase authUseCase;
    
    @Operation(
        summary = "SearchStructureNote 생성",
        description = "SearchStructureNote를 생성합니다."
    )
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "생성 성공"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content),
    })
    @PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<ApiResponse<Void>> post(@RequestBody NotePostReqDto reqDto) {

        final UUID memberId = authUseCase.getMemberId();
        searchStructureNoteUseCase.registerSearchStructureNote(SearchStructureNoteDtoMapper.toDomain(reqDto), memberId);

        return ResponseEntity.ok(
            ApiResponse.withoutData(ResponseCode.SUCCESS, ResponseMessage.SUCCESS_REGISTER.getMessage())
        );
    }

    @Operation(
        summary = "SearchStructureNote 삭제",
        description = "SearchStructureNote를 삭제합니다."
    )
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "삭제 성공"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "존재하지 않거나 권한이 없는 리소스", content = @Content),
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(
        @Parameter(description = "삭제하려는 SearchStructureNote ID", example = "123e4567-e89b-12d3-a456-426614174000")
        @PathVariable("id") UUID id
    ) {

        final UUID memberId = authUseCase.getMemberId();
        searchStructureNoteUseCase.softDeleteSearchStructureNote(id, memberId);

        return ResponseEntity.ok(
            ApiResponse.withoutData(ResponseCode.SUCCESS, ResponseMessage.SUCCESS_DELETE.getMessage())
        );
    }

    @Operation(
        summary = "SearchStructureNote 수정",
        description = "SearchStructureNote 전체 필드를 수정합니다."
    )
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "수정 성공"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "존재하지 않거나 권한이 없는 리소스", content = @Content),
    })
    @PutMapping(consumes = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<ApiResponse<Void>> put(@RequestBody NotePutReqDto reqDto) {

        final UUID memberId = authUseCase.getMemberId();
        searchStructureNoteUseCase.updateSearchStructureNote(SearchStructureNoteDtoMapper.toDomain(reqDto), memberId);

        return ResponseEntity.ok(
            ApiResponse.withoutData(ResponseCode.SUCCESS, ResponseMessage.SUCCESS_UPDATE.getMessage())
        );
    }

    @Operation(
        summary = "특정 SearchStructure와 연결되는 SearchStructureNote 리스트 조회",
        description = "특정 SearchStructure와 연결되는 SearchStructureNote 리스트를 조회합니다."
    )
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "조회 성공"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "존재하지 않거나 권한이 없는 리소스", content = @Content),
    })
    @GetMapping("/list")
    public ResponseEntity<ApiResponse<List<NoteResDto>>> getList(
        @Parameter(description = "SearchStructureNote와 연결된 것들로 조회하려는 SearchStructure ID", example = "123e4567-e89b-12d3-a456-426614174000")
        @RequestParam("searchStructureId") UUID searchStructureId
    ) {

        final UUID memberId = authUseCase.getMemberId();
        final List<NoteResDto> list = searchStructureNoteUseCase.listSearchStructureNotesBySearchStructure(searchStructureId, memberId)
            .stream()
            .map(domain -> SearchStructureNoteDtoMapper.toResDto(domain))
            .collect(Collectors.toList());

        return ResponseEntity.ok(
            ApiResponse.of(ResponseCode.SUCCESS, ResponseMessage.SUCCESS_GET.getMessage(), list)
        );
    }

}
