package com.sshyu.zibnote.adapter.out.persistence.note.jpa.entity;

import org.hibernate.annotations.SQLRestriction;

import com.sshyu.zibnote.adapter.out.persistence.common.BaseEntity;
import com.sshyu.zibnote.adapter.out.persistence.member.jpa.entity.MemberEntity;

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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "NOTE_FIELD")
@SQLRestriction("is_deleted = 0")
@Getter @SuperBuilder
@NoArgsConstructor @AllArgsConstructor
public class NoteFieldEntity extends BaseEntity {
    
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
