package com.sshyu.zibnote.domain.search.port.out;

import java.util.List;
import java.util.UUID;

import com.sshyu.zibnote.domain.search.model.Search;

public interface SearchRepository {
    
    UUID save(Search search);

    Search findBySearchId(UUID searchId);

    List<Search> findAllByMemberId(UUID memberId);

    void softDeleteBySearchId(UUID searchId);
    
}
