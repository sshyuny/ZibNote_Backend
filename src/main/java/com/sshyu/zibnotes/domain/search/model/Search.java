package com.sshyu.zibnotes.domain.search.model;

import java.time.LocalDateTime;
import java.util.UUID;

import com.sshyu.zibnotes.domain.common.BaseFields;
import com.sshyu.zibnotes.domain.common.exception.UnauthorizedAccessException;
import com.sshyu.zibnotes.domain.member.model.Member;
import com.sshyu.zibnotes.domain.search.exception.InvalidSearchException;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder @Getter
public class Search extends BaseFields {
    
    private final UUID searchId;

    private final Member member;

    private final String title;

    private final String region;

    private final String description;

    public static Search ofFull(final UUID searchId, final Member member, final String title, final String region, final String description, 
            final LocalDateTime createdAt, final LocalDateTime updatedAt, final Integer isDeleted) {
        return Search.builder()
            .searchId(searchId)
            .member(member)
            .title(title)
            .region(region)
            .description(description)
            .createdAt(createdAt)
            .updatedAt(updatedAt)
            .isDeleted(isDeleted)
            .build();
    }

    public static Search ofBasic(final UUID searchId, final Member member, final String title, final String region, final String description) {
        return Search.builder()
            .searchId(searchId)
            .member(member)
            .title(title)
            .region(region)
            .description(description)
            .build();
    }

    public static Search onlyId(final UUID searchId) {
        return Search.builder()
                    .searchId(searchId)
                    .build();
    }

    public void assureOwner(final UUID memberId) {
        if (!this.member.getMemberId().equals(memberId)) {
            throw new UnauthorizedAccessException("존재하지 않거나 접근할 수 없는 데이터입니다.");
        }
    }

    /**
     * 수정 가능한 상태인지 검증합니다.
     * 
     * <ul>
     *   <li>Member는 불변해야 하기 때문에 null이 들어가있어야 합니다.</li>
     *   <li>title과 region이 비어있지 않은지 확인합니다.</li>
     * </ul>
     */
    public void validateForUpdate() {
        if (this.searchId == null) { throw new InvalidSearchException(); }
        if (this.member != null) { throw new InvalidSearchException(); }
        if (this.title == null || this.title.isBlank()) { throw new InvalidSearchException(); }
        if (this.region == null || this.region.isBlank()) { throw new InvalidSearchException(); }
    }

}
