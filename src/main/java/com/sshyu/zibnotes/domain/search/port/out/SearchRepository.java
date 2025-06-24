package com.sshyu.zibnotes.domain.search.port.out;

import java.util.List;
import java.util.UUID;

import com.sshyu.zibnotes.domain.search.model.Search;

public interface SearchRepository {
    
    UUID save(Search search);

    Search findBySearchId(UUID searchId);

    List<Search> findAllByMemberId(UUID memberId);

    void softDeleteBySearchId(UUID searchId);

    void update(Search search);
    
}
