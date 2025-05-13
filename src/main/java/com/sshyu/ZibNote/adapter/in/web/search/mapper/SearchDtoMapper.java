package com.sshyu.zibnote.adapter.in.web.search.mapper;

import com.sshyu.zibnote.adapter.in.web.search.dto.SearchResDto;
import com.sshyu.zibnote.domain.search.model.Search;

public class SearchDtoMapper {
    
    public static SearchResDto toResDto(Search domain) {
        return new SearchResDto(
            domain.getSearchId(),
            domain.getTitle(),
            domain.getRegion(),
            domain.getDescription()
        );
    }

}
