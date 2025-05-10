package com.sshyu.zibnote.domain.search.port.in;

import java.util.List;

import com.sshyu.zibnote.domain.search.model.SearchStructureNote;

public interface SearchStructureNoteUseCase {
    
    Long registerSearchStructureNote(SearchStructureNote searchStructureNote, Long loginedMemberId);

    List<SearchStructureNote> listSearchStructureNotesBySearchStructure(Long searchStructureId, Long loginedMemberId);

    void softDeleteSearchStructureNote(Long searchStructureNoteId, Long loginedMemberId);

}
