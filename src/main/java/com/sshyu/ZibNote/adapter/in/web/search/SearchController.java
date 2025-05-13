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
import com.sshyu.zibnote.adapter.in.web.search.dto.SearchDeleteReqDto;
import com.sshyu.zibnote.adapter.in.web.search.dto.SearchReqDto;
import com.sshyu.zibnote.adapter.in.web.search.dto.SearchResDto;
import com.sshyu.zibnote.adapter.in.web.search.mapper.SearchDtoMapper;
import com.sshyu.zibnote.domain.auth.port.in.AuthUseCase;
import com.sshyu.zibnote.domain.member.model.Member;
import com.sshyu.zibnote.domain.search.model.Search;
import com.sshyu.zibnote.domain.search.port.in.SearchUseCase;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/search")
@RestController
public class SearchController {

    private final SearchUseCase searchUseCase;
    private final AuthUseCase authUseCase;
    
    @PostMapping
    public ResponseEntity<ApiResponse<Void>> post(@RequestBody SearchReqDto reqDto) {

        searchUseCase.registerSearch(Search.builder()
            .member(Member.onlyId(authUseCase.getMemberId()))
            .title(reqDto.getTitle())
            .region(reqDto.getRegion())
            .description(reqDto.getDescription())
            .build()
        );

        return ResponseEntity.ok(ApiResponse.successWithMessage("임장 등록 성공!"));
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse<Void>> delete(@RequestBody SearchDeleteReqDto reqDto) {

        searchUseCase.softDeleteSearch(reqDto.getSearchId(), authUseCase.getMemberId());

        return ResponseEntity.ok(ApiResponse.successWithMessage("임장 삭제 성공"));
    }

    @GetMapping("/list")
    public ResponseEntity<ApiResponse<List<SearchResDto>>> getList() {

        final List<SearchResDto> resDtos = searchUseCase.listSearchesByMember(authUseCase.getMemberId())
            .stream()
            .map(domain -> SearchDtoMapper.toResDto(domain))
            .collect(Collectors.toList());

        return ResponseEntity.ok(ApiResponse.successWithData(resDtos));
    }

}
