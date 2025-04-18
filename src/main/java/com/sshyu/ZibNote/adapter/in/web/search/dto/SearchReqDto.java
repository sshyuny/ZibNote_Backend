package com.sshyu.zibnote.adapter.in.web.search.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SearchReqDto {

    private Long searchId;
    
    private String title;

    private String region;

    private String description;

}
