package com.sshyu.zibnote.domain.search.port.in;

import java.util.List;
import java.util.UUID;

import com.sshyu.zibnote.domain.search.model.SearchStructureNote;

public interface SearchStructureNoteUseCase {
    
    UUID registerSearchStructureNote(SearchStructureNote searchStructureNote, Long loginedMemberId);

    List<SearchStructureNote> listSearchStructureNotesBySearchStructure(UUID searchStructureId, Long loginedMemberId);

    void softDeleteSearchStructureNote(UUID searchStructureNoteId, Long loginedMemberId);

}
