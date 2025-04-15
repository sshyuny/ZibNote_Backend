package com.sshyu.zibnote.adapter.out.persistence.search.entity;

import com.sshyu.zibnote.adapter.out.persistence.note.entity.EvalTypeEntity;
import com.sshyu.zibnote.adapter.out.persistence.note.entity.NoteFieldEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@IdClass(SearchStructureNoteId.class)
@Table(name = "SEARCH_STRUCTURE_NOTE")
@Getter @Builder
@NoArgsConstructor @AllArgsConstructor
public class SearchStructureNoteEntity {
    
    @Id
    @ManyToOne
    @JoinColumn(name = "search_structure_id")
    private SearchStructureEntity searchStructureEntity;

    @Id
    @ManyToOne
    @JoinColumn(name = "note_field_id")
    private NoteFieldEntity noteFieldEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "eval_type_id")
    private EvalTypeEntity evalTypeEntity;

    @Column(length = 100)
    private String evalValue;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String note;

}
