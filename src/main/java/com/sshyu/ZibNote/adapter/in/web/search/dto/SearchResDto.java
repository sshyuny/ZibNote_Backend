package com.sshyu.zibnote.adapter.in.web.search.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class SearchResDto {
    
    private Long searchId;

    private String title;

    private String region;

    private String description;

}
