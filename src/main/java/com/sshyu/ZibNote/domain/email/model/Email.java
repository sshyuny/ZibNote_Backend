package com.sshyu.zibnote.domain.email.model;

import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class Email {
    
    private final String from;
    private final String to;
    private final String subject;
    private final String text;

}
