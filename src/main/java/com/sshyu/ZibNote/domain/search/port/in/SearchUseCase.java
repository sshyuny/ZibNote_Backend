package com.sshyu.zibnote.domain.search.port.in;

import java.util.List;

import com.sshyu.zibnote.domain.search.model.Search;

public interface SearchUseCase {
    
    Long registerSearch(Search search);

    Search getSearch(Long searchId);

    List<Search> listSearchesByMember(Long memberId);

    void softDeleteSearch(Long searchId, Long memberId);

    Search assertSearchOwner(Long searchId, Long memberId);

}
