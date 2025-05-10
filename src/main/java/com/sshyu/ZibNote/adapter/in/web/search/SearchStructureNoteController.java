package com.sshyu.zibnote.adapter.in.web.search;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sshyu.zibnote.adapter.in.web.common.ApiResponse;
import com.sshyu.zibnote.adapter.in.web.search.dto.NoteDeleteReqDto;
import com.sshyu.zibnote.adapter.in.web.search.dto.NoteGetListReqDto;
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

        Long memberId = authUseCase.getMemberId();
        searchStructureNoteUseCase.registerSearchStructureNote(SearchStructureNoteDtoMapper.toDomain(reqDto), memberId);

        return ResponseEntity.ok(ApiResponse.successWithMessage("저장 성공!"));
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse<Void>> delete(@RequestBody NoteDeleteReqDto reqDto) {

        Long memberId = authUseCase.getMemberId();
        searchStructureNoteUseCase.softDeleteSearchStructureNote(reqDto.getSearchStructureNoteId(), memberId);

        return ResponseEntity.ok(ApiResponse.successWithMessage("삭제 완료"));
    }

    @GetMapping("/list")
    public ResponseEntity<ApiResponse<List<NoteResDto>>> getList(@RequestBody NoteGetListReqDto reqDto) {

        Long memberId = authUseCase.getMemberId();
        List<NoteResDto> list = searchStructureNoteUseCase.listSearchStructureNotesBySearchStructure(reqDto.getSearchStructureId(), memberId)
            .stream()
            .map(domain -> SearchStructureNoteDtoMapper.toResDto(domain))
            .collect(Collectors.toList());

        return ResponseEntity.ok(ApiResponse.successWithData(list));
    }

}
