package com.sshyu.zibnote.adapter.in.web.note;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sshyu.zibnote.adapter.in.web.common.res.ResponseCode;
import com.sshyu.zibnote.adapter.in.web.common.res.ApiResponse;
import com.sshyu.zibnote.adapter.in.web.common.res.ResponseMessage;
import com.sshyu.zibnote.adapter.in.web.note.dto.NoteFieldDeleteReqDto;
import com.sshyu.zibnote.adapter.in.web.note.dto.NoteFieldReqDto;
import com.sshyu.zibnote.adapter.in.web.note.dto.NoteFieldResDto;
import com.sshyu.zibnote.adapter.in.web.note.mapper.NoteFieldDtoMapper;
import com.sshyu.zibnote.domain.auth.port.in.AuthUseCase;
import com.sshyu.zibnote.domain.note.port.in.NoteFieldUseCase;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/notefield")
@RestController
public class NoteFieldController {

    private final NoteFieldUseCase noteFieldUseCase;
    private final AuthUseCase authUseCase;
    
    @Operation(
        summary = "NoteField 생성",
        description = "NoteField를 생성합니다."
    )
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "생성 성공"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content),
    })
    @PostMapping
    public ResponseEntity<ApiResponse<Void>> post(@RequestBody NoteFieldReqDto reqDto) {

        final UUID loginedMemberId = authUseCase.getMemberId();
        noteFieldUseCase.registerNoteField(NoteFieldDtoMapper.toDomain(reqDto, loginedMemberId));

        return ResponseEntity.ok(
            ApiResponse.withoutData(ResponseCode.SUCCESS, ResponseMessage.SUCCESS_REGISTER.getMessage())
        );
    }

    @Operation(
        summary = "로그인한 사용자가 등록한 Search 리스트 조회",
        description = "로그인한 사용자가 등록한 Search  리스트를 조회합니다."
    )
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "조회 성공"),
    })
    @GetMapping("/list")
    public ResponseEntity<ApiResponse<List<NoteFieldResDto>>> getList() {

        final List<NoteFieldResDto> noteFieldResDtos = noteFieldUseCase.listNoteFieldsByMember(authUseCase.getMemberId())
            .stream()
            .map(domain -> NoteFieldDtoMapper.toResDto(domain))
            .collect(Collectors.toList());

        return ResponseEntity.ok(
            ApiResponse.of(ResponseCode.SUCCESS, ResponseMessage.SUCCESS_GET.getMessage(), noteFieldResDtos)
        );
    }

    @Operation(
        summary = "NoteField 삭제",
        description = "NoteField를 삭제합니다."
    )
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "삭제 성공"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "존재하지 않거나 권한이 없는 리소스", content = @Content),
    })
    @DeleteMapping
    public ResponseEntity<ApiResponse<Void>> delete(@RequestBody NoteFieldDeleteReqDto reqDto) {

        noteFieldUseCase.softDeleteNoteField(reqDto.getNoteFieldId(), authUseCase.getMemberId());

        return ResponseEntity.ok(
            ApiResponse.withoutData(ResponseCode.SUCCESS, ResponseMessage.SUCCESS_DELETE.getMessage())
        );
    }

}
