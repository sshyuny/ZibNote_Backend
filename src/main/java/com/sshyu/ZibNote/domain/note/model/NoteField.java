package com.sshyu.zibnote.domain.note.model;

import com.sshyu.zibnote.domain.common.BaseFields;
import com.sshyu.zibnote.domain.member.model.Member;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder @Getter
public class NoteField extends BaseFields {
    
    private Long noteFieldId;

    private Member member;

    private String name;

    private String description;
    
}
