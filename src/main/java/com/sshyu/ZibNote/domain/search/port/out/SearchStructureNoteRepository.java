package com.sshyu.zibnote.domain.search.port.out;

import java.time.LocalDateTime;
import java.util.List;

import com.sshyu.zibnote.domain.search.model.SearchStructureNote;

public interface SearchStructureNoteRepository {
    
    void save(SearchStructureNote searchStructureNote);

    SearchStructureNote findBySearchStructureNoteId(Long searchStructureNoteId);
    
    List<SearchStructureNote> findAllBySearchStructureId(Long searchStructureId);

    void softDeleteBySearchStructureNoteId(Long searchStructureNoteId, LocalDateTime updatedAt);
    
}
