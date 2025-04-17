package com.sshyu.zibnote.domain.search.model;

import com.sshyu.zibnote.domain.common.BaseFields;
import com.sshyu.zibnote.domain.member.model.Member;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder @Getter
public class Search extends BaseFields {
    
    private Long searchId;

    private Member member;

    private String title;

    private String region;

    private String description;

}
