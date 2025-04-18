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

import com.sshyu.zibnote.adapter.in.web.common.ResBodyForm;
import com.sshyu.zibnote.adapter.in.web.search.dto.SearchReqDto;
import com.sshyu.zibnote.adapter.in.web.search.dto.SearchResDto;
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
    public ResponseEntity<String> post(@RequestBody SearchReqDto reqDto) {

        final Member member = authUseCase.getMember();

        searchUseCase.registerSearch(Search.builder()
            .member(member)
            .title(reqDto.getTitle())
            .region(reqDto.getRegion())
            .description(reqDto.getDescription())
            .build()
        );

        return ResponseEntity.ok("success");
    }

    @DeleteMapping
    public ResponseEntity<String> delete(@RequestBody SearchReqDto reqDto) {

        final Member member = authUseCase.getMember();
        searchUseCase.softDeleteSearch(reqDto.getSearchId(), member.getMemberId());

        return ResponseEntity.ok("success");
    }

    @GetMapping("/list")
    public ResponseEntity<ResBodyForm> getList() {

        final Member member = authUseCase.getMember();

        List<SearchResDto> resDtos = searchUseCase.listSearchesByMember(member.getMemberId())
            .stream()
            .map(domain -> SearchResDto.builder()
                .searchId(domain.getSearchId())
                .title(domain.getTitle())
                .region(domain.getRegion())
                .description(domain.getDescription())
                .build())
            .collect(Collectors.toList());

        return ResponseEntity.ok(ResBodyForm.builder().data(resDtos).build());
    }

}
