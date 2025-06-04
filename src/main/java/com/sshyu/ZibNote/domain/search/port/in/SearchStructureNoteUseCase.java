package com.sshyu.zibnote.domain.search.port.in;

import java.util.List;
import java.util.UUID;

import com.sshyu.zibnote.domain.search.model.SearchStructureNote;

public interface SearchStructureNoteUseCase {
    
    UUID registerSearchStructureNote(SearchStructureNote searchStructureNote, UUID loginedMemberId);

    List<SearchStructureNote> listSearchStructureNotesBySearchStructure(UUID searchStructureId, UUID loginedMemberId);

    void softDeleteSearchStructureNote(UUID searchStructureNoteId, UUID loginedMemberId);

    void updateSearchStructureNote(SearchStructureNote searchStructureNote, UUID loginedMemberId);

}
