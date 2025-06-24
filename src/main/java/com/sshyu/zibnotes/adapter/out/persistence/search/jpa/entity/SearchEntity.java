package com.sshyu.zibnotes.adapter.out.persistence.search.jpa.entity;

import java.util.UUID;

import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.UuidGenerator;

import com.sshyu.zibnotes.adapter.out.persistence.common.BaseEntity;
import com.sshyu.zibnotes.adapter.out.persistence.member.jpa.entity.MemberEntity;
import com.sshyu.zibnotes.domain.search.model.Search;

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

    /**
     * 요청된 도메인 값으로 엔티티 필드를 수정합니다.
     * 
     * <p> ID, Member는 변경되지 않습니다.
     * 
     * @param domain 요청된 도메인
     */
    public void update(Search domain) {
        this.title = domain.getTitle();
        this.region = domain.getRegion();
        this.description = domain.getDescription();
    }

}
