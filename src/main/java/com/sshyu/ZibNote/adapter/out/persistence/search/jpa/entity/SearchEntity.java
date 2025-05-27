package com.sshyu.zibnote.adapter.out.persistence.search.jpa.entity;

import java.util.UUID;

import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.UuidGenerator;

import com.sshyu.zibnote.adapter.out.persistence.common.BaseEntity;
import com.sshyu.zibnote.adapter.out.persistence.member.jpa.entity.MemberEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "SEARCH")
@SQLRestriction("is_deleted = 0")
@Getter @SuperBuilder
@NoArgsConstructor @AllArgsConstructor
public class SearchEntity extends BaseEntity {
    
    @Id @UuidGenerator
    @Column(columnDefinition = "BINARY(16)")
    private UUID searchId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity memberEntity;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(length = 255)
    private String region;

    @Column(length = 255)
    private String description;

    public static SearchEntity ref(final UUID searchId) {
        return SearchEntity.builder()
                    .searchId(searchId)
                    .build();
    }

}
