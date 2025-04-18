package com.sshyu.zibnote.application.service.search;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.sshyu.zibnote.domain.search.model.Search;
import com.sshyu.zibnote.domain.search.port.in.SearchUseCase;
import com.sshyu.zibnote.domain.search.port.out.SearchRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SearchService implements SearchUseCase {

    private final SearchRepository searchRepository;

    @Override
    public void registerSearch(Search search) {

        search.prepareForCreation();
        searchRepository.save(search);
    }

    @Override
    public Search getSearch(Long searchId) {
        return searchRepository.findBySearchId(searchId);
    }

    @Override
    public List<Search> listSearchesByMember(Long memberId) {
        
        return searchRepository.findAllByMemberId(memberId);
    }

    @Override
    public void softDeleteSearch(Long searchId, Long memberId) {

        Search selectedSearch = searchRepository.findBySearchId(searchId);

        selectedSearch.assureOwner(memberId);

        searchRepository.softDeleteBySearchId(selectedSearch.getSearchId(), LocalDateTime.now());
    }
    
    
}
