package com.sshyu.zibnote.domain.member.model;

import com.sshyu.zibnote.domain.common.BaseFields;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder @Getter
public class Member extends BaseFields {
    
    private Long memberId;

    private String name;
    
}
