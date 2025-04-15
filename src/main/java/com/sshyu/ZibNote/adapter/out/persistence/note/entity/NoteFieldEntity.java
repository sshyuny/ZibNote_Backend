package com.sshyu.zibnote.adapter.out.persistence.note.entity;

import com.sshyu.zibnote.adapter.out.persistence.member.entity.MemberEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "NOTE_FIELD")
@Getter @Builder
@NoArgsConstructor @AllArgsConstructor
public class NoteFieldEntity {
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long noteFieldId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity memberEntity;

    @Column(nullable =  false, length = 100)
    private String name;

    @Column(length = 255)
    private String description;

}
