package com.sshyu.zibnote.adapter.in.web.search;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sshyu.zibnote.adapter.in.web.common.res.ResponseCode;
import com.sshyu.zibnote.adapter.in.web.common.res.ApiResponse;
import com.sshyu.zibnote.adapter.in.web.common.res.ResponseMessage;
import com.sshyu.zibnote.adapter.in.web.search.dto.SearchReqDto;
import com.sshyu.zibnote.adapter.in.web.search.dto.SearchResDto;
import com.sshyu.zibnote.adapter.in.web.search.mapper.SearchDtoMapper;
import com.sshyu.zibnote.domain.auth.port.in.AuthUseCase;
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

        final UUID memberId = authUseCase.getMemberId();
        searchUseCase.registerSearch(SearchDtoMapper.toDomain(reqDto, memberId));

        return ResponseEntity.ok(
            ApiResponse.withoutData(ResponseCode.SUCCESS, ResponseMessage.SUCCESS_REGISTER.getMessage())
        );
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable("id") UUID id) {

        searchUseCase.softDeleteSearch(id, authUseCase.getMemberId());

        return ResponseEntity.ok(
            ApiResponse.withoutData(ResponseCode.SUCCESS, ResponseMessage.SUCCESS_DELETE.getMessage())
        );
    }

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
