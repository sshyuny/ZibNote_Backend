package com.sshyu.zibnote.domain.search.port.in;

import java.util.List;
import java.util.UUID;

import com.sshyu.zibnote.domain.search.model.Search;

public interface SearchUseCase {
    
    UUID registerSearch(Search search);

    Search getSearch(UUID searchId);

    List<Search> listSearchesByMember(Long memberId);

    void softDeleteSearch(UUID searchId, Long memberId);

    Search assertSearchOwner(UUID searchId, Long memberId);

}
