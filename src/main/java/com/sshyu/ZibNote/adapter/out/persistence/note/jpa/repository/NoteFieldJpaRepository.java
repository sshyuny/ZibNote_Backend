package com.sshyu.zibnote.adapter.out.persistence.note.jpa.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sshyu.zibnote.adapter.out.persistence.member.jpa.entity.MemberEntity;
import com.sshyu.zibnote.adapter.out.persistence.note.jpa.entity.NoteFieldEntity;

public interface NoteFieldJpaRepository extends JpaRepository<NoteFieldEntity, Long> {
    
    Optional<NoteFieldEntity> findByMemberEntityAndName(MemberEntity memberEntity, String name);

    List<NoteFieldEntity> findAllByMemberEntity(MemberEntity memberEntity);

    @Modifying
    @Query("UPDATE NoteFieldEntity n SET n.isDeleted = 1, n.updatedAt = :now WHERE n.noteFieldId = :noteFieldId")
    void softDeleteByNoteFieldId(@Param("noteFieldId") Long noteFieldId, @Param("now") LocalDateTime now);

}
