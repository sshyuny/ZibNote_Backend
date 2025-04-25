package com.sshyu.zibnote.domain.search.port.out;

import java.util.List;

import com.sshyu.zibnote.domain.search.model.SearchStructureNote;

public interface SearchStructureNoteRepository {
    
    Long save(SearchStructureNote searchStructureNote);

    SearchStructureNote findBySearchStructureNoteId(Long searchStructureNoteId);
    
    List<SearchStructureNote> findAllBySearchStructureId(Long searchStructureId);

    void softDeleteBySearchStructureNoteId(Long searchStructureNoteId);
    
}
