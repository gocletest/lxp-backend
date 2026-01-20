package com.gocle.lxp.controller;

import com.gocle.lxp.common.ApiResponse;
import com.gocle.lxp.dto.institution.InstitutionUserDto;
import com.gocle.lxp.service.InstitutionUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/institution-users")
@RequiredArgsConstructor
public class InstitutionUserController {

    private final InstitutionUserService institutionUserService;

    /** 목록 / 검색 */
    @GetMapping
    public ApiResponse<List<InstitutionUserDto>> list(
            @RequestParam(name = "clientId", required = false) Long clientId,
            @RequestParam(name = "clientName", required = false) String clientName,
            @RequestParam(name = "loginId", required = false) String loginId,
            @RequestParam(name = "userName", required = false) String userName,
            @RequestParam(name = "status", required = false) String status
    ) {
        return ApiResponse.success(
            "기관 사용자 목록",
            institutionUserService.getInstitutionUsers(
                clientId, clientName, loginId, userName, status
            )
        );
    }

    /** 단건 조회 */
    @GetMapping("/{institutionUserId}")
    public ApiResponse<InstitutionUserDto> detail(
            @PathVariable("institutionUserId") Long institutionUserId
    ) {
        return ApiResponse.success(
            "기관 사용자 상세",
            institutionUserService.getInstitutionUser(institutionUserId)
        );
    }

    /** 🔥 기관 사용자 등록 */
    @PostMapping
    public ApiResponse<Void> create(
            @RequestBody InstitutionUserDto dto
    ) {
        institutionUserService.createInstitutionUser(dto);
        return ApiResponse.success("기관 사용자 등록 완료", null);
    }

    /** 사용자명 수정 */
    @PutMapping("/{institutionUserId}")
    public ApiResponse<Void> update(
            @PathVariable("institutionUserId") Long institutionUserId,
            @RequestBody InstitutionUserDto dto
    ) {
        dto.setInstitutionUserId(institutionUserId);
        institutionUserService.updateInstitutionUser(dto);
        return ApiResponse.success("수정 완료", null);
    }

    /** 비밀번호 초기화 */
    @PatchMapping("/{institutionUserId}/reset-password")
    public ApiResponse<String> resetPassword(
            @PathVariable("institutionUserId") Long institutionUserId
    ) {
        return ApiResponse.success(
            "비밀번호 초기화 완료",
            institutionUserService.resetPassword(institutionUserId)
        );
    }

    /** 상태 변경 */
    @PatchMapping("/{institutionUserId}/status")
    public ApiResponse<Void> changeStatus(
            @PathVariable("institutionUserId") Long institutionUserId,
            @RequestParam("status") String status
    ) {
        institutionUserService.changeStatus(institutionUserId, status);
        return ApiResponse.success("상태 변경 완료", null);
    }

    /** 삭제 */
    @DeleteMapping("/{institutionUserId}")
    public ApiResponse<Void> delete(
            @PathVariable("institutionUserId") Long institutionUserId
    ) {
        institutionUserService.deleteInstitutionUser(institutionUserId);
        return ApiResponse.success("삭제 완료", null);
    }
}

