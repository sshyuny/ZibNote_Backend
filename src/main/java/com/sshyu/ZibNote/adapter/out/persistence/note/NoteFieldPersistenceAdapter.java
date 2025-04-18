package com.sshyu.zibnote.adapter.out.persistence.note;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.sshyu.zibnote.adapter.out.persistence.member.jpa.entity.MemberEntity;
import com.sshyu.zibnote.adapter.out.persistence.note.jpa.entity.NoteFieldEntity;
import com.sshyu.zibnote.adapter.out.persistence.note.jpa.repository.NoteFieldJpaRepository;
import com.sshyu.zibnote.adapter.out.persistence.note.mapper.NoteFieldMapper;
import com.sshyu.zibnote.domain.member.model.Member;
import com.sshyu.zibnote.domain.note.exception.NoteFieldNotFoundException;
import com.sshyu.zibnote.domain.note.model.NoteField;
import com.sshyu.zibnote.domain.note.port.out.NoteFieldRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class NoteFieldPersistenceAdapter implements NoteFieldRepository {

    private final NoteFieldJpaRepository noteFieldJpaRepository;

    @Override
    public void save(NoteField noteField) {

        MemberEntity detachedMember = MemberEntity.builder()
            .memberId(noteField.getMember().getMemberId())
            .build();

        noteFieldJpaRepository.save(
            NoteFieldEntity.builder()
                .memberEntity(detachedMember)
                .name(noteField.getName())
                .description(noteField.getDescription())
                .createdAt(noteField.getCreatedAt())
                .updatedAt(noteField.getUpdatedAt())
                .isDeleted(noteField.getIsDeleted())
                .build()
        );
    }

    @Override
    public NoteField findByNoteFieldId(Long noteFieldId) {

        NoteFieldEntity noteFieldEntity = noteFieldJpaRepository.findById(noteFieldId)
            .orElseThrow(() -> new NoteFieldNotFoundException());

        return NoteField.builder()
                .noteFieldId(noteFieldEntity.getNoteFieldId())
                .member(Member.builder().memberId(noteFieldEntity.getMemberEntity().getMemberId()).build())
                .name(noteFieldEntity.getName())
                .description(noteFieldEntity.getDescription())
                .createdAt(noteFieldEntity.getCreatedAt())
                .updatedAt(noteFieldEntity.getUpdatedAt())
                .isDeleted(noteFieldEntity.getIsDeleted())
                .build();
    }

    @Override
    public NoteField findByMemberAndName(Long memberId, String name) {

        NoteFieldEntity noteFieldEntity = noteFieldJpaRepository.findByMemberEntityAndName(
            MemberEntity.builder().memberId(memberId).build(), name)
            .orElseThrow(() -> new NoteFieldNotFoundException());

        return NoteField.builder()
            .noteFieldId(noteFieldEntity.getNoteFieldId())
            .member(Member.builder().memberId(noteFieldEntity.getMemberEntity().getMemberId()).build())
            .name(noteFieldEntity.getName())
            .description(noteFieldEntity.getDescription())
            .createdAt(noteFieldEntity.getCreatedAt())
            .updatedAt(noteFieldEntity.getUpdatedAt())
            .isDeleted(noteFieldEntity.getIsDeleted())
            .build();
    }

    @Override
    public List<NoteField> findAllByMember(Long memberId) {
        return noteFieldJpaRepository.findAllByMemberEntity(MemberEntity.builder().memberId(memberId).build())
            .stream()
            .map(entity -> NoteFieldMapper.toDomain(entity))
            .collect(Collectors.toList());
    }

    @Override
    public void softDeleteByNoteFieldId(Long noteFieldId, LocalDateTime updatedAt) {
        noteFieldJpaRepository.softDeleteByNoteFieldId(noteFieldId, updatedAt);
    }
    
}
