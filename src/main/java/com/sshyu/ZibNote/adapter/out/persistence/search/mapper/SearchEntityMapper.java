package com.sshyu.zibnote.adapter.out.persistence.search.mapper;

import com.sshyu.zibnote.adapter.out.persistence.member.mapper.MemberEntityMapper;
import com.sshyu.zibnote.adapter.out.persistence.search.jpa.entity.SearchEntity;
import com.sshyu.zibnote.domain.search.exception.SearchNotFoundException;
import com.sshyu.zibnote.domain.search.model.Search;

public class SearchEntityMapper {
    
    public static Search toDomain(SearchEntity entity) {

        if (entity == null) { throw new SearchNotFoundException(); }

        return Search.builder()
                    .searchId(entity.getSearchId())
                    .member(MemberEntityMapper.toDomain(entity.getMemberEntity()))
                    .title(entity.getTitle())
                    .region(entity.getRegion())
                    .description(entity.getDescription())
                    .createdAt(entity.getCreatedAt())
                    .updatedAt(entity.getUpdatedAt())
                    .isDeleted(entity.getIsDeleted())
                    .build();
    }

    public static SearchEntity toEntity(Search domain) {

        if (domain == null) {return SearchEntity.builder().build();}

        return SearchEntity.builder()
                    .searchId(domain.getSearchId())
                    .memberEntity(MemberEntityMapper.toEntity(domain.getMember()))
                    .title(domain.getTitle())
                    .region(domain.getRegion())
                    .description(domain.getDescription())
                    .createdAt(domain.getCreatedAt())
                    .updatedAt(domain.getUpdatedAt())
                    .isDeleted(domain.getIsDeleted())
                    .build();
    }

}
