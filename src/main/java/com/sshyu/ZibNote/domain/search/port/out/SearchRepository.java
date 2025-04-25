package com.sshyu.zibnote.domain.search.port.out;

import java.util.List;

import com.sshyu.zibnote.domain.search.model.Search;

public interface SearchRepository {
    
    Long save(Search search);

    Search findBySearchId(Long searchId);

    List<Search> findAllByMemberId(Long memberId);

    void softDeleteBySearchId(Long searchId);
    
}
