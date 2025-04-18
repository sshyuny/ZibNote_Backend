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

import com.sshyu.zibnote.adapter.in.web.common.ResBodyForm;
import com.sshyu.zibnote.adapter.in.web.note.dto.NoteFieldReqDto;
import com.sshyu.zibnote.adapter.in.web.note.dto.NoteFieldResDto;
import com.sshyu.zibnote.domain.auth.port.in.AuthUseCase;
import com.sshyu.zibnote.domain.member.model.Member;
import com.sshyu.zibnote.domain.note.model.NoteField;
import com.sshyu.zibnote.domain.note.port.in.NoteFieldUseCase;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/notefield")
@RestController
public class NoteFieldController {

    private final NoteFieldUseCase noteFieldUseCase;
    private final AuthUseCase authUseCase;
    
    @PostMapping
    public ResponseEntity<String> post(@RequestBody NoteFieldReqDto reqDto) {

        final Member member = authUseCase.getMember();

        noteFieldUseCase.registerNoteField(
            NoteField.builder()
                .member(member)
                .name(reqDto.getName())
                .description(reqDto.getDescription())
                .build()
        );

        return ResponseEntity.ok("success");
    }

    @GetMapping("/list")
    public ResponseEntity<ResBodyForm> getList() {

        final Member member = authUseCase.getMember();

        List<NoteFieldResDto> noteFieldResDtos = noteFieldUseCase.listNoteFieldsByMember(member.getMemberId())
            .stream()
            .map(domain -> NoteFieldResDto.builder()
                .noteFieldId(domain.getNoteFieldId())
                .name(domain.getName())
                .description(domain.getDescription())
                .build())
            .collect(Collectors.toList());

        return ResponseEntity.ok(ResBodyForm.builder().data(noteFieldResDtos).build());
    }

    @DeleteMapping
    public ResponseEntity<String> delete(@RequestBody NoteFieldReqDto reqDto) {

        noteFieldUseCase.softDeleteNoteField(
            NoteField.builder()
                .noteFieldId(reqDto.getNoteFieldId())
                .build()
        );

        return ResponseEntity.ok("success");
    }

}
