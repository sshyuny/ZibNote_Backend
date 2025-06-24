package com.sshyu.zibnotes.adapter.in.web.search.mapper;

import java.util.UUID;

import com.sshyu.zibnotes.adapter.in.web.search.dto.SearchPutReqDto;
import com.sshyu.zibnotes.adapter.in.web.search.dto.SearchReqDto;
import com.sshyu.zibnotes.adapter.in.web.search.dto.SearchResDto;
import com.sshyu.zibnotes.domain.member.model.Member;
import com.sshyu.zibnotes.domain.search.model.Search;

public class SearchDtoMapper {
    
    public static SearchResDto toResDto(final Search domain) {
        return new SearchResDto(
            domain.getSearchId().toString(),
            domain.getTitle(),
            domain.getRegion(),
            domain.getDescription()
        );
    }

    public static Search toDomain(final SearchReqDto reqDto, final UUID memberId) {
        return Search.builder()
            .member(Member.onlyId(memberId))
            .title(reqDto.getTitle())
            .region(reqDto.getRegion())
            .description(reqDto.getDescription())
            .build();
    }

    /**
     * PUT 요청 DTO를 도메인 객체로 변환하여 반환합니다.
     * 
     * <p> Member는 불변 필드이기 때문에 항상 <b>null</b>을 넣어줍니다.
     * 
     * @param reqDto 수정하려는 내용이 담긴 DTO
     * @return 변환된 도메인
     */
    public static Search fromPutReqDtoToDomain(final SearchPutReqDto reqDto) {
        return Search.ofBasic(
            reqDto.getSearchId(), 
            null, 
            reqDto.getTitle(), 
            reqDto.getRegion(), 
            reqDto.getDescription()
        );
    }

}
