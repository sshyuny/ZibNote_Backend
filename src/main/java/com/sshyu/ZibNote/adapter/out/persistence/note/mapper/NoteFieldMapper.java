package com.sshyu.zibnote.adapter.out.persistence.note.mapper;

import com.sshyu.zibnote.adapter.out.persistence.member.mapper.MemberMapper;
import com.sshyu.zibnote.adapter.out.persistence.note.jpa.entity.NoteFieldEntity;
import com.sshyu.zibnote.domain.note.model.NoteField;

public class NoteFieldMapper {

    public static NoteField toDomain(NoteFieldEntity entity) {
        return NoteField.builder()
                    .noteFieldId(entity.getNoteFieldId())
                    .member(MemberMapper.toDomain(entity.getMemberEntity()))
                    .name(entity.getName())
                    .description(entity.getDescription())
                    .createdAt(entity.getCreatedAt())
                    .updatedAt(entity.getUpdatedAt())
                    .isDeleted(entity.getIsDeleted())
                    .build();
    }
    
    public static NoteFieldEntity toEntity(NoteField domain) {
        return NoteFieldEntity.builder()
                    .noteFieldId(domain.getNoteFieldId())
                    .memberEntity(MemberMapper.toEntity(domain.getMember()))
                    .name(domain.getName())
                    .description(domain.getDescription())
                    .createdAt(domain.getCreatedAt())
                    .updatedAt(domain.getUpdatedAt())
                    .isDeleted(domain.getIsDeleted())
                    .build();
    }
}
