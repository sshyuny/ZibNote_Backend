package com.sshyu.zibnote.adapter.out.persistence.search;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sshyu.zibnote.adapter.out.persistence.search.jpa.entity.SearchStructureEntity;
import com.sshyu.zibnote.adapter.out.persistence.search.jpa.entity.SearchStructureNoteEntity;
import com.sshyu.zibnote.adapter.out.persistence.search.jpa.repository.SearchStructureNoteJpaRepository;
import com.sshyu.zibnote.adapter.out.persistence.search.mapper.SearchStructureNoteEntityMapper;
import com.sshyu.zibnote.domain.search.exception.SearchStructureNoteNotFoundException;
import com.sshyu.zibnote.domain.search.model.SearchStructureNote;
import com.sshyu.zibnote.domain.search.port.out.SearchStructureNoteRepository;

import lombok.RequiredArgsConstructor;

@Transactional
@RequiredArgsConstructor
@Repository
public class SearchStructureNotePersistenceAdapter implements SearchStructureNoteRepository {
    
    private final SearchStructureNoteJpaRepository searchStructureNoteJpaRepository;

    @Override
    public Long save(SearchStructureNote searchStructureNote) {
        SearchStructureNoteEntity entity = SearchStructureNoteEntityMapper.toEntity(searchStructureNote);
        searchStructureNoteJpaRepository.save(entity);
        return entity.getSearchStructureNoteId();
    }

    @Override
    public SearchStructureNote findBySearchStructureNoteId(Long searchStructureNoteId) {

        SearchStructureNoteEntity searchStructureEntity = searchStructureNoteJpaRepository.findById(searchStructureNoteId)
            .orElseThrow(() -> new SearchStructureNoteNotFoundException());
        
        return SearchStructureNoteEntityMapper.toDomain(searchStructureEntity);
    }

    @Override
    public List<SearchStructureNote> findAllBySearchStructureId(Long searchStructureId) {

        final SearchStructureEntity searchStructureRef = SearchStructureEntity.builder().searchStructureId(searchStructureId).build();

        return searchStructureNoteJpaRepository.findAllBySearchStructureEntity(searchStructureRef).stream()
                    .map(entity -> SearchStructureNoteEntityMapper.toDomain(entity))
                    .collect(Collectors.toList());
    }

    @Override
    public void softDeleteBySearchStructureNoteId(Long searchStructureNoteId) {
        SearchStructureNoteEntity entity = searchStructureNoteJpaRepository.findById(searchStructureNoteId)
            .orElseThrow(() -> new SearchStructureNoteNotFoundException());
        entity.softDelete();
        searchStructureNoteJpaRepository.save(entity);
    }

}
