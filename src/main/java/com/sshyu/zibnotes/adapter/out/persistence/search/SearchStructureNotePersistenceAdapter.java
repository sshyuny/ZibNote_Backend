package com.sshyu.zibnotes.adapter.out.persistence.search;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sshyu.zibnotes.adapter.out.persistence.search.jpa.entity.SearchStructureEntity;
import com.sshyu.zibnotes.adapter.out.persistence.search.jpa.entity.SearchStructureNoteEntity;
import com.sshyu.zibnotes.adapter.out.persistence.search.jpa.repository.SearchStructureNoteJpaRepository;
import com.sshyu.zibnotes.adapter.out.persistence.search.mapper.SearchStructureNoteEntityMapper;
import com.sshyu.zibnotes.domain.search.exception.SearchStructureNoteNotFoundException;
import com.sshyu.zibnotes.domain.search.model.SearchStructureNote;
import com.sshyu.zibnotes.domain.search.port.out.SearchStructureNoteRepository;

import lombok.RequiredArgsConstructor;

@Transactional
@RequiredArgsConstructor
@Repository
public class SearchStructureNotePersistenceAdapter implements SearchStructureNoteRepository {
    
    private final SearchStructureNoteJpaRepository searchStructureNoteJpaRepository;

    @Override
    public UUID save(final SearchStructureNote searchStructureNote) {

        final SearchStructureNoteEntity entity = SearchStructureNoteEntityMapper.toEntity(searchStructureNote);
        searchStructureNoteJpaRepository.save(entity);

        return entity.getSearchStructureNoteId();
    }

    @Override
    public SearchStructureNote findBySearchStructureNoteId(final UUID searchStructureNoteId) {

        final SearchStructureNoteEntity entity = searchStructureNoteJpaRepository.findById(searchStructureNoteId)
            .orElseThrow(() -> new SearchStructureNoteNotFoundException());
        
        return SearchStructureNoteEntityMapper.toDomain(entity);
    }

    @Override
    public List<SearchStructureNote> findAllBySearchStructureId(final UUID searchStructureId) {

        final SearchStructureEntity searchStructureRef = SearchStructureEntity.ref(searchStructureId);

        return searchStructureNoteJpaRepository.findAllBySearchStructureEntity(searchStructureRef).stream()
                    .map(entity -> SearchStructureNoteEntityMapper.toDomain(entity))
                    .collect(Collectors.toList());
    }

    @Override
    public void softDeleteBySearchStructureNoteId(final UUID searchStructureNoteId) {

        final SearchStructureNoteEntity entity = searchStructureNoteJpaRepository.findById(searchStructureNoteId)
            .orElseThrow(() -> new SearchStructureNoteNotFoundException());
        
        entity.softDelete();
        searchStructureNoteJpaRepository.save(entity);
    }

    @Override
    public void updateBySearchStructureNoteId(final SearchStructureNote searchStructureNote) {
        
        final SearchStructureNoteEntity entity = searchStructureNoteJpaRepository.findById(searchStructureNote.getSearchStructureNoteId())
            .orElseThrow(() -> new SearchStructureNoteNotFoundException());

        entity.update(searchStructureNote);
        searchStructureNoteJpaRepository.save(entity);
    }

}
