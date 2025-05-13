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

import com.sshyu.zibnote.adapter.in.web.common.ApiResponse;
import com.sshyu.zibnote.adapter.in.web.search.dto.SearchStructureDeleteReqDto;
import com.sshyu.zibnote.adapter.in.web.search.dto.SearchStructureReqDto;
import com.sshyu.zibnote.adapter.in.web.search.dto.SearchStructureResDto;
import com.sshyu.zibnote.adapter.in.web.search.mapper.SearchStructureDtoMapper;
import com.sshyu.zibnote.domain.auth.port.in.AuthUseCase;
import com.sshyu.zibnote.domain.search.port.in.SearchStructureUseCase;

import lombok.RequiredArgsConstructor;

@RequestMapping("/api/search-structure")
@RestController
@RequiredArgsConstructor
public class SearchStructureController {
    
    private final SearchStructureUseCase searchStructureUseCase;
    private final AuthUseCase authUseCase;

    @PostMapping
    public ResponseEntity<ApiResponse<Void>> post(@RequestBody SearchStructureReqDto searchStructureReqDto) {

        final Long loginedMemberId = authUseCase.getMemberId();
        searchStructureUseCase.registerSearchStructure(
            SearchStructureDtoMapper.toDomain(searchStructureReqDto), loginedMemberId
        );

        return ResponseEntity.ok(ApiResponse.successWithMessage("임장 건물 추가 성공!"));
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse<Void>> delete(@RequestBody SearchStructureDeleteReqDto reqDto) {

        final Long loginedMemberId = authUseCase.getMemberId();
        searchStructureUseCase.softDeleteSearchStructure(reqDto.getSearchStructureId(), loginedMemberId);

        return ResponseEntity.ok(ApiResponse.successWithMessage("임장 건물 삭제 성공!"));
    }

    @GetMapping("/list")
    public ResponseEntity<ApiResponse<List<SearchStructureResDto>>> getList(@RequestParam("searchId") Long searchId) {

        final Long loginedMemberId = authUseCase.getMemberId();
        final List<SearchStructureResDto> searchStructureDtos = searchStructureUseCase.listSearchStructuresBySearch(searchId, loginedMemberId)
            .stream()
            .map(domain -> SearchStructureDtoMapper.toResDto(domain))
            .collect(Collectors.toList());

        return ResponseEntity.ok(ApiResponse.successWithData(searchStructureDtos));
    }

}
