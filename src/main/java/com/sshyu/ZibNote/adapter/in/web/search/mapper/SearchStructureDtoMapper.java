package com.sshyu.zibnote.adapter.in.web.search.mapper;

import com.sshyu.zibnote.adapter.in.web.search.dto.SearchStructureReqDto;
import com.sshyu.zibnote.adapter.in.web.search.dto.SearchStructureResDto;
import com.sshyu.zibnote.domain.search.model.Search;
import com.sshyu.zibnote.domain.search.model.SearchStructure;
import com.sshyu.zibnote.domain.structure.model.Structure;

public class SearchStructureDtoMapper {
    
    public static SearchStructureResDto toResDto(final SearchStructure domain) {
        return new SearchStructureResDto(
            domain.getSearchStructureId(),
            domain.getSearch().getSearchId(),
            domain.getSearch().getTitle(),
            domain.getStructure().getStructureId(),
            domain.getStructure().getName(),
            domain.getDescription()
        );
    }

    public static SearchStructure toDomain(final SearchStructureReqDto reqDto) {
        return SearchStructure.builder()
                    .search(Search.onlyId(reqDto.getSearchId()))
                    .structure(Structure.onlyId(reqDto.getStructureId()))
                    .description(reqDto.getDescription())
                    .build();
    }

}
