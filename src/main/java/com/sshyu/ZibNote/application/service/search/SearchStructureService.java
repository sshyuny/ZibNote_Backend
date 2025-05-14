package com.sshyu.zibnote.application.service.search;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sshyu.zibnote.domain.member.exception.UnauthorizedAccessException;
import com.sshyu.zibnote.domain.search.exception.NotValidSearchStructureException;
import com.sshyu.zibnote.domain.search.exception.SearchNotFoundException;
import com.sshyu.zibnote.domain.search.model.Search;
import com.sshyu.zibnote.domain.search.model.SearchStructure;
import com.sshyu.zibnote.domain.search.port.in.SearchStructureUseCase;
import com.sshyu.zibnote.domain.search.port.in.SearchUseCase;
import com.sshyu.zibnote.domain.search.port.out.SearchStructureRepository;
import com.sshyu.zibnote.domain.structure.exception.StructureNotFoundException;
import com.sshyu.zibnote.domain.structure.port.in.StructureUseCase;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SearchStructureService implements SearchStructureUseCase {

    private final SearchStructureRepository searchStructureRepository;
    private final SearchUseCase searchUseCase;
    private final StructureUseCase structureUseCase;

    /**
     * SearchStructure를 등록한다.
     * 
     * <ol>
     *   <li>SearchStructure 유효성 검사</li>
     *   <li>로그인한 사용자가 Search에 접근 가능한지 확인</li>
     *   <li>Structure 엔티티 존재하는지 확인</li>
     *   <li>SearchStructure 생성</li>
     * </ol>
     * 
     * @param searchStructure 등록하려는 SearchStructure
     * @param loginedMemberId 로그인한 사용자 ID
     * @throws NotValidSearchStructureException SearchStructure 유효성 검사에 실패 시
     * @throws SearchNotFoundException Search가 존재하지 않는 경우
     * @throws UnauthorizedAccessException 로그인한 사용자가 Search를 참조할 권한이 없는 경우
     * @throws StructureNotFoundException Structure가 존재하지 않을 경우
     */
    @Override
    public Long registerSearchStructure(final SearchStructure searchStructure, final Long loginedMemberId) {

        searchStructure.validate();

        final Search search = searchUseCase.getSearch(searchStructure.getSearch().getSearchId());
        search.assureOwner(loginedMemberId);
        
        structureUseCase.getStructure(searchStructure.getStructure().getStructureId());

        return searchStructureRepository.save(searchStructure);
    }

    /**
     * Search ID로 연결된 SearchStructure들을 조회한다.
     * 
     * <ol>
     *   <li>로그인한 사용자가 Search에 접근 가능한지 확인</li>
     *   <li>Search ID에 연결된 Structure 목록 조회</li>
     * </ol>
     * 
     * @param sesarchId SearchStructure와 연결된 것들로 조회하려는 Search ID
     * @param loginedMemberId 로그인한 사용자 ID
     * @return Search ID에 연결된 Structure 목록
     * @throws UnauthorizedAccessException Search 주인이 로그인된 Member와 다를 경우
     */
    @Override
    public List<SearchStructure> listSearchStructuresBySearch(final Long searchId, final Long loginedMemberId) {

        final Search search = searchUseCase.getSearch(searchId);
        search.assureOwner(loginedMemberId);

        return searchStructureRepository.findAllBySearchId(searchId);
    }

    /**
     * SearchStructure를 삭제한다.
     * 
     * <ol>
     *   <li>로그인한 사용자가 SearchStructure(내부 Search)에 접근 가능한지 확인</li>
     *   <li>SearchStructure 삭제</li>
     * </ol>
     * 
     * @param searchStructureId 삭제하려는 SearchStructure ID
     * @param loginedMemberId 로그인한 사용자 ID
     * @throws UnauthorizedAccessException Search 주인이 로그인된 Member와 다를 경우
     */
    @Override
    public void softDeleteSearchStructure(final Long searchStructureId, final Long loginedMemberId) {

        assertSearchStructureOwner(searchStructureId, loginedMemberId);

        searchStructureRepository.softDeleteBySearchStructureId(searchStructureId);
    }

    /**
     * 로그인한 계정이 SearchStructure에 접근 가능한지 확인한다.
     * 
     * <ol>
     *   <li>SearchStructure와 그 참조 엔티티 Search 조회</li>
     *   <li>로그인한 사용자가 Search에 접근 가능한지 확인</li>
     * </ol>
     * 
     * @param searchStructureId 확인 대상 ID
     * @param loginedMemberId 로그인한 사용자 ID
     * @return 확인 완료된 SearchStructure
     * @throws UnauthorizedAccessException Search 주인이 로그인된 Member와 다를 경우
     */
    @Override
    public SearchStructure assertSearchStructureOwner(final Long searchStructureId, final Long loginedMemberId) {

        final SearchStructure searchStructure = searchStructureRepository.findBySearchStructureId(searchStructureId);
        searchUseCase.assertSearchOwner(searchStructure.getSearch().getSearchId(), loginedMemberId);

        return searchStructure;
    }
    
}
