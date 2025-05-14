package com.sshyu.zibnote.adapter.in.web.search.mapper;

import com.sshyu.zibnote.adapter.in.web.search.dto.SearchReqDto;
import com.sshyu.zibnote.adapter.in.web.search.dto.SearchResDto;
import com.sshyu.zibnote.domain.member.model.Member;
import com.sshyu.zibnote.domain.search.model.Search;

public class SearchDtoMapper {
    
    public static SearchResDto toResDto(final Search domain) {
        return new SearchResDto(
            domain.getSearchId(),
            domain.getTitle(),
            domain.getRegion(),
            domain.getDescription()
        );
    }

    public static Search toDomain(final SearchReqDto reqDto, final Long memberId) {
        return Search.builder()
            .member(Member.onlyId(memberId))
            .title(reqDto.getTitle())
            .region(reqDto.getRegion())
            .description(reqDto.getDescription())
            .build();
    }

}
