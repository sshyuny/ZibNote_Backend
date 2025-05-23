package com.sshyu.zibnote.adapter.in.web.search;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sshyu.zibnote.adapter.in.web.common.res.ResponseCode;
import com.sshyu.zibnote.adapter.in.web.common.res.ApiResponse;
import com.sshyu.zibnote.adapter.in.web.common.res.ResponseMessage;
import com.sshyu.zibnote.adapter.in.web.search.dto.NoteDeleteReqDto;
import com.sshyu.zibnote.adapter.in.web.search.dto.NotePostReqDto;
import com.sshyu.zibnote.adapter.in.web.search.dto.NoteResDto;
import com.sshyu.zibnote.adapter.in.web.search.mapper.SearchStructureNoteDtoMapper;
import com.sshyu.zibnote.domain.auth.port.in.AuthUseCase;
import com.sshyu.zibnote.domain.search.port.in.SearchStructureNoteUseCase;

import lombok.RequiredArgsConstructor;

@RequestMapping("/api/search-structure-note")
@RequiredArgsConstructor
@RestController
public class SearchStructureNoteController {

    private final SearchStructureNoteUseCase searchStructureNoteUseCase;
    private final AuthUseCase authUseCase;
    
    @PostMapping
    public ResponseEntity<ApiResponse<Void>> post(@RequestBody NotePostReqDto reqDto) {

        final Long memberId = authUseCase.getMemberId();
        searchStructureNoteUseCase.registerSearchStructureNote(SearchStructureNoteDtoMapper.toDomain(reqDto), memberId);

        return ResponseEntity.ok(
            ApiResponse.withoutData(ResponseCode.SUCCESS, ResponseMessage.SUCCESS_REGISTER.getMessage())
        );
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse<Void>> delete(@RequestBody NoteDeleteReqDto reqDto) {

        final Long memberId = authUseCase.getMemberId();
        searchStructureNoteUseCase.softDeleteSearchStructureNote(reqDto.getSearchStructureNoteId(), memberId);

        return ResponseEntity.ok(
            ApiResponse.withoutData(ResponseCode.SUCCESS, ResponseMessage.SUCCESS_DELETE.getMessage())
        );
    }

    @GetMapping("/list")
    public ResponseEntity<ApiResponse<List<NoteResDto>>> getList(@RequestParam("searchStructureId") Long searchStructureId) {

        final Long memberId = authUseCase.getMemberId();
        final List<NoteResDto> list = searchStructureNoteUseCase.listSearchStructureNotesBySearchStructure(searchStructureId, memberId)
            .stream()
            .map(domain -> SearchStructureNoteDtoMapper.toResDto(domain))
            .collect(Collectors.toList());

        return ResponseEntity.ok(
            ApiResponse.of(ResponseCode.SUCCESS, ResponseMessage.SUCCESS_GET.getMessage(), list)
        );
    }

}
