package com.sshyu.zibnotes.adapter.out.persistence.note;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sshyu.zibnotes.adapter.out.persistence.member.jpa.entity.MemberEntity;
import com.sshyu.zibnotes.adapter.out.persistence.note.jpa.entity.NoteFieldEntity;
import com.sshyu.zibnotes.adapter.out.persistence.note.jpa.repository.NoteFieldJpaRepository;
import com.sshyu.zibnotes.adapter.out.persistence.note.mapper.NoteFieldEntityMapper;
import com.sshyu.zibnotes.domain.member.model.Member;
import com.sshyu.zibnotes.domain.note.exception.NoteFieldNotFoundException;
import com.sshyu.zibnotes.domain.note.model.NoteField;
import com.sshyu.zibnotes.domain.note.port.out.NoteFieldRepository;

import lombok.RequiredArgsConstructor;

@Transactional
@RequiredArgsConstructor
@Repository
public class NoteFieldPersistenceAdapter implements NoteFieldRepository {

    private final NoteFieldJpaRepository noteFieldJpaRepository;

    @Override
    public Long save(final NoteField noteField) {

        final MemberEntity memberRef = MemberEntity.ref(noteField.getMember().getMemberId());

        final NoteFieldEntity entity = noteFieldJpaRepository.save(
            NoteFieldEntity.builder()
                .memberEntity(memberRef)
                .name(noteField.getName())
                .description(noteField.getDescription())
                .build()
        );

        return entity.getNoteFieldId();
    }

    @Override
    public NoteField findByNoteFieldId(final Long noteFieldId) {

        final NoteFieldEntity noteFieldEntity = noteFieldJpaRepository.findById(noteFieldId)
            .orElseThrow(() -> new NoteFieldNotFoundException());

        return NoteFieldEntityMapper.toDomain(noteFieldEntity);
    }

    @Override
    public NoteField findByMemberAndName(final UUID memberId, final String name) {

        final NoteFieldEntity noteFieldEntity = noteFieldJpaRepository.findByMemberEntityAndName(
                MemberEntity.ref(memberId), name)
            .orElseThrow(() -> new NoteFieldNotFoundException());

        return NoteField.builder()
            .noteFieldId(noteFieldEntity.getNoteFieldId())
            .member(Member.onlyId(noteFieldEntity.getMemberEntity().getMemberId()))
            .name(noteFieldEntity.getName())
            .description(noteFieldEntity.getDescription())
            .createdAt(noteFieldEntity.getCreatedAt())
            .updatedAt(noteFieldEntity.getUpdatedAt())
            .isDeleted(noteFieldEntity.getIsDeleted())
            .build();
    }

    @Override
    public List<NoteField> findAllByMemberId(final UUID memberId) {

        return noteFieldJpaRepository.findAllByMemberEntity(MemberEntity.ref(memberId))
            .stream()
            .map(entity -> NoteFieldEntityMapper.toDomain(entity))
            .collect(Collectors.toList());
    }

    @Override
    public void softDeleteByNoteFieldId(final Long noteFieldId) {

        final NoteFieldEntity noteFieldEntity = noteFieldJpaRepository.findById(noteFieldId)
            .orElseThrow(() -> new NoteFieldNotFoundException());

        noteFieldEntity.softDelete();
        noteFieldJpaRepository.save(noteFieldEntity);
    }
    
}
