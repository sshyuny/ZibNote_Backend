package com.sshyu.zibnotes.adapter.out.persistence.search.jpa.entity;

import java.util.UUID;

import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.UuidGenerator;

import com.sshyu.zibnotes.adapter.out.persistence.common.BaseEntity;
import com.sshyu.zibnotes.adapter.out.persistence.note.jpa.entity.NoteFieldEntity;
import com.sshyu.zibnotes.domain.search.model.EvalType;
import com.sshyu.zibnotes.domain.search.model.SearchStructureNote;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "SEARCH_STRUCTURE_NOTE")
@SQLRestriction("is_deleted = 0")
@Getter @SuperBuilder
@NoArgsConstructor @AllArgsConstructor
public class SearchStructureNoteEntity extends BaseEntity {
    
    @Id @UuidGenerator
    @Column(columnDefinition = "BINARY(16)")
    private UUID searchStructureNoteId;

    @ManyToOne
    @JoinColumn(name = "search_structure_id")
    private SearchStructureEntity searchStructureEntity;

    @ManyToOne
    @JoinColumn(name = "note_field_id")
    private NoteFieldEntity noteFieldEntity;

    @Enumerated(EnumType.STRING)
    @Column(length = 100)
    private EvalType evalType;

    @Column(length = 100)
    private String evalValue;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String note;

    public void update(SearchStructureNote domain) {
        this.noteFieldEntity = NoteFieldEntity.ref(domain.getNoteField().getNoteFieldId());
        this.evalType = domain.getEvalType();
        this.evalValue = domain.getEvalValue();
        this.note = domain.getNote();
    }

}
