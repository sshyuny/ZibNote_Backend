package com.sshyu.zibnote.adapter.in.web.note;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sshyu.zibnote.adapter.in.web.common.ResBodyForm;
import com.sshyu.zibnote.adapter.in.web.note.dto.NoteFieldReqDto;
import com.sshyu.zibnote.domain.auth.port.in.AuthUseCase;
import com.sshyu.zibnote.domain.member.model.Member;
import com.sshyu.zibnote.domain.member.port.in.MemberUseCase;
import com.sshyu.zibnote.domain.note.model.NoteField;
import com.sshyu.zibnote.domain.note.port.in.NoteFieldUseCase;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/notefield")
@RestController
public class NoteFieldController {

    private final NoteFieldUseCase noteFieldUseCase;
    private final MemberUseCase memberUseCase;
    private final AuthUseCase authUseCase;
    
    @PostMapping
    public ResBodyForm post(@RequestBody NoteFieldReqDto reqDto) {

        final String memberName = authUseCase.getMemberName();
        final Member member = memberUseCase.findByName(memberName);
        final LocalDateTime now = LocalDateTime.now();

        noteFieldUseCase.save(
            NoteField.builder()
                .member(member)
                .name(reqDto.getName())
                .description(reqDto.getDescription())
                .createdAt(now)
                .updatedAt(now)
                .isDeleted(0)
                .build()
        );

        return ResBodyForm.builder().data("success").build();
    }

    @GetMapping
    public ResBodyForm get() {

        final String memberName = authUseCase.getMemberName();
        final Member member = memberUseCase.findByName(memberName);

        List<NoteField> noteFields = noteFieldUseCase.findByMember(member.getMemberId());

        return ResBodyForm.builder().data(noteFields).build();
    }

}
