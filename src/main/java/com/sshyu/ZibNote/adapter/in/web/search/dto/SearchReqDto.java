package com.sshyu.zibnote.adapter.in.web.search.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class SearchReqDto {
    
    @NotBlank
    private String title;

    @NotBlank
    private String region;

    private String description;

}
