package com.sshyu.zibnotes.adapter.out.persistence.note.jpa.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sshyu.zibnotes.adapter.out.persistence.member.jpa.entity.MemberEntity;
import com.sshyu.zibnotes.adapter.out.persistence.note.jpa.entity.NoteFieldEntity;

public interface NoteFieldJpaRepository extends JpaRepository<NoteFieldEntity, Long> {
    
    Optional<NoteFieldEntity> findByMemberEntityAndName(MemberEntity memberEntity, String name);

    List<NoteFieldEntity> findAllByMemberEntity(MemberEntity memberEntity);

}
