package com.sshyu.zibnotes.domain.search.port.out;

import java.util.List;
import java.util.UUID;

import com.sshyu.zibnotes.domain.search.model.SearchStructureNote;

public interface SearchStructureNoteRepository {
    
    UUID save(SearchStructureNote searchStructureNote);

    SearchStructureNote findBySearchStructureNoteId(UUID searchStructureNoteId);
    
    List<SearchStructureNote> findAllBySearchStructureId(UUID searchStructureId);

    void softDeleteBySearchStructureNoteId(UUID searchStructureNoteId);

    void updateBySearchStructureNoteId(SearchStructureNote searchStructureNote);
    
}
