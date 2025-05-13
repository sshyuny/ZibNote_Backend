package com.sshyu.zibnote.adapter.in.web.search.mapper;

import com.sshyu.zibnote.adapter.in.web.search.dto.SearchStructureReqDto;
import com.sshyu.zibnote.adapter.in.web.search.dto.SearchStructureResDto;
import com.sshyu.zibnote.domain.search.model.Search;
import com.sshyu.zibnote.domain.search.model.SearchStructure;
import com.sshyu.zibnote.domain.structure.model.Structure;

public class SearchStructureDtoMapper {
    
    public static SearchStructureResDto toResDto(final SearchStructure domain) {
        return SearchStructureResDto.builder()
                    .searchStructureId(domain.getSearchStructureId())
                    .searchId(domain.getSearch().getSearchId())
                    .searchTitle(domain.getSearch().getTitle())
                    .structureId(domain.getStructure().getStructureId())
                    .structureName(domain.getStructure().getName())
                    .description(domain.getDescription())
                    .build();
    }

    public static SearchStructure toDomain(final SearchStructureReqDto reqDto) {
        return SearchStructure.builder()
                    .search(Search.onlyId(reqDto.getSearchId()))
                    .structure(Structure.onlyId(reqDto.getStructureId()))
                    .description(reqDto.getDescription())
                    .build();
    }

}
