package com.sshyu.zibnote.application.service.search;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.sshyu.zibnote.domain.common.exception.UnauthorizedAccessException;
import com.sshyu.zibnote.domain.search.exception.SearchNotFoundException;
import com.sshyu.zibnote.domain.search.model.Search;
import com.sshyu.zibnote.domain.search.port.in.SearchUseCase;
import com.sshyu.zibnote.domain.search.port.out.SearchRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SearchService implements SearchUseCase {

    private final SearchRepository searchRepository;

    /**
     * Search를 등록한다.
     * 
     * @param search 등록하려는 Search
     */
    @Override
    public UUID registerSearch(final Search search) {

        return searchRepository.save(search);
    }

    /**
     * ID로 Search를 조회한다.
     * 
     * @param searchId 조회 대상 ID
     * @return ID에 일치하는 Search
     * @throws SearchNotFoundException 일치하는 Search가 없는 경우
     */
    @Override
    public Search getSearch(final UUID searchId) {

        return searchRepository.findBySearchId(searchId);
    }

    /**
     * 로그인한 사용자가 등록한 Search 목록을 조회한다.
     * 
     * @param memberId 로그인한 사용자 ID
     * @return 로그인한 사용자가 등록한 Search 목록
     */
    @Override
    public List<Search> listSearchesByMember(final UUID memberId) {
        
        return searchRepository.findAllByMemberId(memberId);
    }

    /**
     * Search를 삭제한다.
     * 
     * <ol>
     *   <li>로그인한 사용자가 Search에 접근 가능한지 확인</li>
     *   <li>Search 삭제</li>
     * </ol>
     * 
     * @param searchId 삭제하려는 Search ID
     * @param memberId 로그인한 사용자 ID
     * @throws UnauthorizedAccessException 로그인한 사용자가 Search 접근 권한 없는 경우
     */
    @Override
    public void softDeleteSearch(final UUID searchId, final UUID memberId) {

        assertSearchOwner(searchId, memberId);

        searchRepository.softDeleteBySearchId(searchId);
    }

    /**
     * 로그인한 사용자가 Search에 접근 가능한지 확인한다.
     * 
     * <ol>
     *   <li>ID로 Search 조회</li>
     *   <li>로그인한 사용자가 Search에 접근 가능한지 확인</li>
     * </ol>
     * 
     * @param searchId 확인 대상 ID
     * @param memberId 로그인한 사용자 ID
     * @return 확인 완료된 Search
     * @throws UnauthorizedAccessException 주어진 계정에 Search 접근 권한 없는 경우
     */
    @Override
    public Search assertSearchOwner(final UUID searchId, final UUID memberId) {

        final Search search = searchRepository.findBySearchId(searchId);
        search.assureOwner(memberId);

        return search;
    }
    
}
