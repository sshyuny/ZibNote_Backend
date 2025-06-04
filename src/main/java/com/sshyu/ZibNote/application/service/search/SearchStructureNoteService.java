package com.sshyu.zibnote.application.service.search;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.sshyu.zibnote.domain.common.exception.AlreadyDeletedException;
import com.sshyu.zibnote.domain.common.exception.UnauthorizedAccessException;
import com.sshyu.zibnote.domain.search.exception.InvalidSearchStructureNoteException;
import com.sshyu.zibnote.domain.search.exception.SearchStructureNoteNotFoundException;
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
     * @throws InvalidSearchStructureNoteException SearchStructureNote 유효성 검사 실패 시
     * @throws UnauthorizedAccessException 로그인 계정이 Search를 참조할 권한이 없는 경우
     */
    @Override
    public UUID registerSearchStructureNote(final SearchStructureNote searchStructureNote, final UUID loginedMemberId) {

        searchStructureNote.validateForRegister();

        assertSearchStructureNoteOwner(searchStructureNote, loginedMemberId);

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
            final UUID searchStructureId, final UUID loginedMemberId) {

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
    public void softDeleteSearchStructureNote(final UUID searchStructureNoteId, final UUID loginedMemberId) {

        final SearchStructureNote note = searchStructureNoteRepository.findBySearchStructureNoteId(searchStructureNoteId);

        assertSearchStructureNoteOwner(note, loginedMemberId);

        searchStructureNoteRepository.softDeleteBySearchStructureNoteId(searchStructureNoteId);
    }

    /**
     * SearchStructureNote를 수정한다.
     * 
     * <ol>
     *   <li>수정 유효성 검사</li>
     *   <li>로그인 계정이 SearchStructureNote(내부 Search)에 접근이 가능한지 확인</li>
     *   <li>SearchStructureNote 수정</li>
     * </ol>
     * 
     * @param searchStructureNote 수정할 내용
     * @param loginedMemberId 로그인한 사용자 ID
     * @throws SearchStructureNoteNotFoundException SearchStructureNote가 존재하지 않는 경우
     * @throws AlreadyDeletedException 이미 삭제된 SearchStructureNote인 경우
     * @throws InvalidSearchStructureNoteException 유효성 검사 실패시
     * @throws UnauthorizedAccessException 로그인 계정이 Search를 참조할 권한이 없는 경우
     */
    @Override
    public void updateSearchStructureNote(final SearchStructureNote searchStructureNote, final UUID loginedMemberId) {

        final SearchStructureNote note = searchStructureNoteRepository.findBySearchStructureNoteId(
            searchStructureNote.getSearchStructureNoteId());

        note.validateForUpdate();

        assertSearchStructureNoteOwner(note, loginedMemberId);

        searchStructureNoteRepository.updateBySearchStructureNoteId(searchStructureNote);
    }

    @Override
    public void assertSearchStructureNoteOwner(final SearchStructureNote searchStructureNote, final UUID loginedMemberId) {

        searchStructureUseCase.assertSearchStructureOwner(
            searchStructureNote.getSearchStructure().getSearchStructureId(),loginedMemberId);
    }

}
