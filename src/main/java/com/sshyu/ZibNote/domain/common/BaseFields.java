package com.sshyu.zibnote.domain.common;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder @Getter
public abstract class BaseFields {
    
    protected LocalDateTime createdAt;
    protected LocalDateTime updatedAt;
    protected int isDeleted;

}
