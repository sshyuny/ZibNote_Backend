package com.sshyu.zibnote.adapter.in.web.search.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter @Setter
public class SearchResDto {
    
    private Long searchId;

    private String title;

    private String region;

    private String description;

}
