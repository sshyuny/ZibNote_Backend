package com.sshyu.zibnote.adapter.in.web.note;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sshyu.zibnote.adapter.in.web.common.ApiResponse;
import com.sshyu.zibnote.adapter.in.web.note.dto.NoteFieldDeleteReqDto;
import com.sshyu.zibnote.adapter.in.web.note.dto.NoteFieldReqDto;
import com.sshyu.zibnote.adapter.in.web.note.dto.NoteFieldResDto;
import com.sshyu.zibnote.adapter.in.web.note.mapper.NoteFieldDtoMapper;
import com.sshyu.zibnote.domain.auth.port.in.AuthUseCase;
import com.sshyu.zibnote.domain.note.port.in.NoteFieldUseCase;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/notefield")
@RestController
public class NoteFieldController {

    private final NoteFieldUseCase noteFieldUseCase;
    private final AuthUseCase authUseCase;
    
    @PostMapping
    public ResponseEntity<ApiResponse<Void>> post(@RequestBody NoteFieldReqDto reqDto) {

        final Long loginedMemberId = authUseCase.getMemberId();
        noteFieldUseCase.registerNoteField(NoteFieldDtoMapper.toDomain(reqDto, loginedMemberId));

        return ResponseEntity.ok(ApiResponse.successWithMessage("조사항목 추가 성공"));
    }

    @GetMapping("/list")
    public ResponseEntity<ApiResponse<List<NoteFieldResDto>>> getList() {

        final List<NoteFieldResDto> noteFieldResDtos = noteFieldUseCase.listNoteFieldsByMember(authUseCase.getMemberId())
            .stream()
            .map(domain -> NoteFieldDtoMapper.toResDto(domain))
            .collect(Collectors.toList());

        return ResponseEntity.ok(ApiResponse.successWithData(noteFieldResDtos));
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse<Void>> delete(@RequestBody NoteFieldDeleteReqDto reqDto) {

        noteFieldUseCase.softDeleteNoteField(reqDto.getNoteFieldId(), authUseCase.getMemberId());

        return ResponseEntity.ok(ApiResponse.successWithMessage("조사항목 삭제 성공"));
    }

}
