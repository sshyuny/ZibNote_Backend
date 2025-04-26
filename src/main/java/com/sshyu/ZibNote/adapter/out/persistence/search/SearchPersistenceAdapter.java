package com.sshyu.zibnote.adapter.out.persistence.search;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sshyu.zibnote.adapter.out.persistence.member.jpa.entity.MemberEntity;
import com.sshyu.zibnote.adapter.out.persistence.search.jpa.entity.SearchEntity;
import com.sshyu.zibnote.adapter.out.persistence.search.jpa.repository.SearchJpaRepository;
import com.sshyu.zibnote.adapter.out.persistence.search.mapper.SearchEntityMapper;
import com.sshyu.zibnote.domain.search.exception.SearchNotFoundException;
import com.sshyu.zibnote.domain.search.model.Search;
import com.sshyu.zibnote.domain.search.port.out.SearchRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Repository
public class SearchPersistenceAdapter implements SearchRepository {
    
    private final SearchJpaRepository searchJpaRepository;

    @Override
    public Long save(final Search search) {

        final MemberEntity memberRef = MemberEntity.builder()
            .memberId(search.getMember().getMemberId())
            .build();

        SearchEntity searchEntity = SearchEntity.builder()
            .memberEntity(memberRef)
            .title(search.getTitle() )
            .region(search.getRegion())
            .description(search.getDescription())
            .build();
            
        searchJpaRepository.save(searchEntity);
        return searchEntity.getSearchId();        
    }

    @Override
    public Search findBySearchId(final Long searchId) {

        SearchEntity searchEntity = searchJpaRepository.findById(searchId)
            .orElseThrow(() -> new SearchNotFoundException());

        return SearchEntityMapper.toDomain(searchEntity);
    }

    @Override
    public List<Search> findAllByMemberId(final Long memberId) {

        final MemberEntity memberRef = MemberEntity.builder()
            .memberId(memberId)
            .build();

        return searchJpaRepository.findAllByMemberEntityAndIsDeleted(memberRef, 0).stream()
                    .map(entity -> SearchEntityMapper.toDomain(entity))
                    .collect(Collectors.toList());
    }

    @Override
    public void softDeleteBySearchId(final Long searchId) {
        SearchEntity searchEntity = searchJpaRepository.findById(searchId)
            .orElseThrow(() -> new SearchNotFoundException());
        searchEntity.softDelete();
        searchJpaRepository.save(searchEntity);
    }

}
