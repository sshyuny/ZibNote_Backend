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
     * <ol>
     *   <li>도메인 검증: SearchStructure 도메인 검증</li>
     *   <li>데이터 주인 확인: Search 주인이 로그인된 Member와 동일한지 확인</li>
     *   <li>데이터 존재 확인: Structure 데이터 존재하는지 확인</li>
     * </ol>
     * 
     * SearchStructure를 생성한다.
     * 
     * @throws NotValidSearchStructureException SearchStructure 도메인 검증에 실패할 경우
     * @throws SearchNotFoundException Search가 존재하지 않을 경우
     * @throws UnauthorizedAccessException Search 주인이 로그인된 Member와 다를 경우
     * @throws StructureNotFoundException Structure가 존재하지 않을 경우
     */
    @Override
    public void registerSearchStructure(SearchStructure searchStructure, Long loginedMemberId) {

        searchStructure.validate();

        Search search = searchUseCase.getSearch(searchStructure.getSearch().getSearchId());
        search.assureOwner(loginedMemberId);
        
        structureUseCase.getStructure(searchStructure.getStructure().getStructureId());

        searchStructureRepository.save(searchStructure);
    }

    /**
     * <ol>
     *   <li>데이터 주인 확인: Search 주인이 로그인된 Member와 동일한지 확인</li>
     * </ol>
     * 
     * Search로 등록된 Structure들을 반환한다.
     * 
     * @throws UnauthorizedAccessException Search 주인이 로그인된 Member와 다를 경우
     */
    @Override
    public List<SearchStructure> listSearchStructuresBySearch(Long searchId, Long loginedMemberId) {

        Search search = searchUseCase.getSearch(searchId);
        search.assureOwner(loginedMemberId);

        return searchStructureRepository.findAllBySearchId(searchId);
    }

    /**
     * <ol>
     *   <li>데이터 주인 확인: Search 주인이 로그인된 Member와 동일한지 확인</li>
     * </ol>
     * 
     * 데이터를 삭제한다.
     * 
     * @throws UnauthorizedAccessException Search 주인이 로그인된 Member와 다를 경우
     */
    @Override
    public void softDeleteSearchStructure(Long searchStructureId, Long loginedMemberId) {

        SearchStructure searchStructure = searchStructureRepository.findBySearchStructureId(searchStructureId);
        searchStructure.getSearch().assureOwner(loginedMemberId);

        searchStructureRepository.softDeleteBySearchStructureId(searchStructureId);
    }
    
}
