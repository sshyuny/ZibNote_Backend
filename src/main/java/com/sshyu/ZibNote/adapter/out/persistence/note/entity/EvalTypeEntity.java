package com.sshyu.zibnote.adapter.out.persistence.note.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "EVAL_TYPE")
@Getter @Builder
@NoArgsConstructor @AllArgsConstructor
public class EvalTypeEntity {
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long evalTypeId;

    @Column(nullable =  false, length = 30)
    private String name;

    @Column(nullable =  false, length = 30)
    private String code;

    @Column(length = 255)
    private String description;


}
