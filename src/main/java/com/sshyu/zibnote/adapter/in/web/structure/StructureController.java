package com.sshyu.zibnote.adapter.in.web.structure;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sshyu.zibnote.adapter.in.web.common.res.ApiResponse;
import com.sshyu.zibnote.adapter.in.web.common.res.ResponseCode;
import com.sshyu.zibnote.adapter.in.web.common.res.ResponseMessage;
import com.sshyu.zibnote.adapter.in.web.structure.dto.StructureResDto;
import com.sshyu.zibnote.adapter.in.web.structure.mapper.StructureDtoMapper;
import com.sshyu.zibnote.domain.structure.port.in.StructureUseCase;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/structure")
@RequiredArgsConstructor
public class StructureController {

    @Autowired
    private final StructureUseCase structureUseCase;
    
    @GetMapping("/list")
    public ResponseEntity<ApiResponse<List<StructureResDto>>> listAddress(
            @RequestParam(value = "address", required = false) String address, 
            @RequestParam(value = "name", required = false) String name) {
    
        if (address != null && !address.isEmpty()) {
            final List<StructureResDto> resDtos = structureUseCase.listStructuresByAddress(address).stream()
                .map(domain -> StructureDtoMapper.toResDto(domain))
                .collect(Collectors.toList());
            return ResponseEntity.ok(
                ApiResponse.of(ResponseCode.SUCCESS, ResponseMessage.SUCCESS_GET.getMessage(), resDtos)
            );
        }

        final List<StructureResDto> resDtos = structureUseCase.listStructuresByName(name).stream()
            .map(domain -> StructureDtoMapper.toResDto(domain))
            .collect(Collectors.toList());
        return ResponseEntity.ok(
            ApiResponse.of(ResponseCode.SUCCESS, ResponseMessage.SUCCESS_GET.getMessage(), resDtos)
        );
    }

}
