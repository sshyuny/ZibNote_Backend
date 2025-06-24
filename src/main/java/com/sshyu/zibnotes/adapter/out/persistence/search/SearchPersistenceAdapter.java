package com.sshyu.zibnotes.adapter.out.persistence.search;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sshyu.zibnotes.adapter.out.persistence.member.jpa.entity.MemberEntity;
import com.sshyu.zibnotes.adapter.out.persistence.search.jpa.entity.SearchEntity;
import com.sshyu.zibnotes.adapter.out.persistence.search.jpa.repository.SearchJpaRepository;
import com.sshyu.zibnotes.adapter.out.persistence.search.mapper.SearchEntityMapper;
import com.sshyu.zibnotes.domain.search.exception.SearchNotFoundException;
import com.sshyu.zibnotes.domain.search.model.Search;
import com.sshyu.zibnotes.domain.search.port.out.SearchRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Repository
public class SearchPersistenceAdapter implements SearchRepository {
    
    private final SearchJpaRepository searchJpaRepository;

    @Override
    public UUID save(final Search search) {

        final MemberEntity memberRef = MemberEntity.ref(search.getMember().getMemberId());

        final SearchEntity searchEntity = SearchEntity.builder()
            .memberEntity(memberRef)
            .title(search.getTitle() )
            .region(search.getRegion())
            .description(search.getDescription())
            .build();
            
        searchJpaRepository.save(searchEntity);
        return searchEntity.getSearchId();        
    }

    @Override
    public Search findBySearchId(final UUID searchId) {

        final SearchEntity searchEntity = searchJpaRepository.findById(searchId)
            .orElseThrow(() -> new SearchNotFoundException());

        return SearchEntityMapper.toDomain(searchEntity);
    }

    @Override
    public List<Search> findAllByMemberId(final UUID memberId) {

        final MemberEntity memberRef = MemberEntity.ref(memberId);

        return searchJpaRepository.findAllByMemberEntity(memberRef).stream()
                    .map(entity -> SearchEntityMapper.toDomain(entity))
                    .collect(Collectors.toList());
    }

    @Override
    public void softDeleteBySearchId(final UUID searchId) {

        final SearchEntity searchEntity = searchJpaRepository.findById(searchId)
            .orElseThrow(() -> new SearchNotFoundException());

        searchEntity.softDelete();
        searchJpaRepository.save(searchEntity);
    }

    @Override
    public void update(Search search) {
        
        final SearchEntity searchEntity = searchJpaRepository.findById(search.getSearchId())
            .orElseThrow(() -> new SearchNotFoundException());

        searchEntity.update(search);
        searchJpaRepository.save(searchEntity);
    }

}
