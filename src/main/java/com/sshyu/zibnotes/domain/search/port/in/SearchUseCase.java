package com.sshyu.zibnotes.domain.search.port.in;

import java.util.List;
import java.util.UUID;

import com.sshyu.zibnotes.domain.search.model.Search;

public interface SearchUseCase {
    
    UUID registerSearch(Search search);

    Search getSearch(UUID searchId);

    List<Search> listSearchesByMember(UUID memberId);

    void updateSearch(Search search, UUID memberId);

    void softDeleteSearch(UUID searchId, UUID memberId);

    Search assertSearchOwner(UUID searchId, UUID memberId);

}
