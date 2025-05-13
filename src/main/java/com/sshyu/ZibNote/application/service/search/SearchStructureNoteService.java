package com.sshyu.zibnote.application.service.search;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sshyu.zibnote.domain.member.exception.UnauthorizedAccessException;
import com.sshyu.zibnote.domain.search.exception.NotValidSearchStructureNoteException;
import com.sshyu.zibnote.domain.search.model.SearchStructureNote;
import com.sshyu.zibnote.domain.search.port.in.SearchStructureNoteUseCase;
import com.sshyu.zibnote.domain.search.port.in.SearchStructureUseCase;
import com.sshyu.zibnote.domain.search.port.out.SearchStructureNoteRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SearchStructureNoteService implements SearchStructureNoteUseCase {

    private final SearchStructureNoteRepository searchStructureNoteRepository;
    private final SearchStructureUseCase searchStructureUseCase;

    /**
     * SearchStructureNote를 등록한다.
     * 
     * <ol>
     *   <li>SearchStructureNote 유효성 검사</li>
     *   <li>로그인 계정이 SearchStructure(내부 Search)에 접근 가능한지 확인</li>
     *   <li>SearchStructureNote 저장</li>
     * </ol>
     * 
     * @param searchStructureNote 등록하려는 SearchStructureNote
     * @param loginedMemberId 로그인한 사용자 ID
     * @throws NotValidSearchStructureNoteException SearchStructureNote 유효성 검사 실패 시
     * @throws UnauthorizedAccessException 로그인 계정이 Search를 참조할 권한이 없는 경우
     */
    @Override
    public Long registerSearchStructureNote(final SearchStructureNote searchStructureNote, final Long loginedMemberId) {

        searchStructureNote.validateForRegister();

        searchStructureUseCase.assertSearchStructureOwner(
            searchStructureNote.getSearchStructure().getSearchStructureId(), loginedMemberId);

        return searchStructureNoteRepository.save(searchStructureNote);
    }

    /**
     * SearchStructure ID로 연결된 SearchStructureNote들을 조회한다.
     * 
     * <ol>
     *   <li>로그인 계정이 SearchStructure(내부 Search)에 접근 가능한지 확인</li>
     *   <li>SearchStructureNote 목록 조회</li>
     * </ol>
     * 
     * @param searchStructureId SearchStructureNote와 연결된 것들로 조회하려는 SearchStructure ID
     * @param loginedMemberId 로그인한 사용자 ID
     * @return SearchStructure ID와 연결된 SearchStructureNote 목록
     * @throws UnauthorizedAccessException 로그인 계정이 Search를 참조할 권한이 없는 경우
     */
    @Override
    public List<SearchStructureNote> listSearchStructureNotesBySearchStructure(
            final Long searchStructureId, final Long loginedMemberId) {

        searchStructureUseCase.assertSearchStructureOwner(searchStructureId, loginedMemberId);

        return searchStructureNoteRepository.findAllBySearchStructureId(searchStructureId);
    }

    /**
     * SearchStructureNote를 삭제한다.
     * 
     * <ol>
     *   <li>SearchStructureNote를 ID로 조회</li>
     *   <li>로그인 계정이 SearchStructureNote(내부 Search)에 접근이 가능한지 확인</li>
     *   <li>SearchStructureNote 삭제</li>
     * </ol>
     * 
     * @param searchStructureNoteId 삭제하려는 SearchStructureNote ID
     * @param loginedMemberId 로그인한 사용자 ID
     * @throws UnauthorizedAccessException 로그인 계정이 Search를 참조할 권한이 없는 경우
     */
    @Override
    public void softDeleteSearchStructureNote(final Long searchStructureNoteId, final Long loginedMemberId) {

        final SearchStructureNote note = searchStructureNoteRepository.findBySearchStructureNoteId(searchStructureNoteId);

        searchStructureUseCase.assertSearchStructureOwner(
            note.getSearchStructure().getSearchStructureId(),loginedMemberId);

        searchStructureNoteRepository.softDeleteBySearchStructureNoteId(searchStructureNoteId);
    }
    
}
