package com.sshyu.zibnote.adapter.in.web.search.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class SearchDeleteReqDto {
    
    @NotNull
    private Long searchId;
    
}
