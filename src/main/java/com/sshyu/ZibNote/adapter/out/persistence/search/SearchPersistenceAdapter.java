package com.sshyu.zibnote.adapter.out.persistence.search;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sshyu.zibnote.adapter.out.persistence.member.jpa.entity.MemberEntity;
import com.sshyu.zibnote.adapter.out.persistence.search.jpa.entity.SearchEntity;
import com.sshyu.zibnote.adapter.out.persistence.search.jpa.repository.SearchJpaRepository;
import com.sshyu.zibnote.domain.member.model.Member;
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
    public void save(final Search search) {

        final MemberEntity memberRef = MemberEntity.builder()
            .memberId(search.getMember().getMemberId())
            .build();

        searchJpaRepository.save(
            SearchEntity.builder()
                .memberEntity(memberRef)
                .title(search.getTitle() )
                .region(search.getRegion())
                .description(search.getDescription())
                .createdAt(search.getCreatedAt())
                .updatedAt(search.getUpdatedAt())
                .isDeleted(search.getIsDeleted())
                .build()
        );        
    }

    @Override
    public Search findBySearchId(final Long searchId) {

        final SearchEntity searchEntity = searchJpaRepository.findBySearchId(searchId)
            .orElseThrow(() -> new SearchNotFoundException());

        return Search.builder()
                .searchId(searchEntity.getSearchId())
                .member(Member.builder().memberId(searchEntity.getMemberEntity().getMemberId()).build())
                .title(searchEntity.getTitle())
                .region(searchEntity.getRegion())
                .description(searchEntity.getDescription())
                .createdAt(searchEntity.getCreatedAt())
                .updatedAt(searchEntity.getUpdatedAt())
                .isDeleted(searchEntity.getIsDeleted())
                .build();
    }

    @Override
    public List<Search> findAllByMemberId(final Long memberId) {

        final MemberEntity memberRef = MemberEntity.builder()
            .memberId(memberId)
            .build();

        return searchJpaRepository.findAllByMemberEntity(memberRef).stream()
                    .map(result -> Search.builder()
                            .searchId(result.getSearchId())
                            .member(Member.builder().memberId(memberId).build())
                            .title(result.getTitle())
                            .region(result.getRegion())
                            .description(result.getDescription())
                            .createdAt(result.getCreatedAt())
                            .updatedAt(result.getUpdatedAt())
                            .isDeleted(result.getIsDeleted())
                            .build())
                    .collect(Collectors.toList());
    }

    @Override
    public void softDeleteBySearchId(final Long searchId, final LocalDateTime updatedAt) {
        searchJpaRepository.softDeleteBySearchId(searchId, updatedAt);
    }

}
