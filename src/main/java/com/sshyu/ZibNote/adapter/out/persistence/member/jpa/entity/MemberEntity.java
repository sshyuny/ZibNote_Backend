package com.sshyu.zibnote.adapter.out.persistence.member.jpa.entity;

import com.sshyu.zibnote.adapter.out.persistence.common.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "MEMBER", uniqueConstraints = {@UniqueConstraint(
    name = "member_name_unique",
    columnNames = {"name"}
)})
@Getter @SuperBuilder
@NoArgsConstructor @AllArgsConstructor
public class MemberEntity extends BaseEntity {
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column(nullable = false, length = 50)
    private String name;
    
    public static MemberEntity ref(Long memberId) {
        return MemberEntity.builder()
                    .memberId(memberId)
                    .build();
    }
}
