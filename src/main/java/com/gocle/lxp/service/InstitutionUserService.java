package com.gocle.lxp.service;

import com.gocle.lxp.domain.InstitutionUser;
import com.gocle.lxp.dto.institution.InstitutionUserDto;
import com.gocle.lxp.mapper.InstitutionUserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class InstitutionUserService {

    private final InstitutionUserMapper institutionUserMapper;
    private final PasswordEncoder passwordEncoder;

    /** 목록 / 검색 */
    public List<InstitutionUserDto> getInstitutionUsers(
            Long clientId,
            String clientName,
            String loginId,
            String userName,
            String status
    ) {
        return institutionUserMapper.selectInstitutionUsers(
            clientId, clientName, loginId, userName, status
        );
    }

    /** 단건 조회 */
    public InstitutionUserDto getInstitutionUser(Long institutionUserId) {
        return institutionUserMapper.selectById(institutionUserId);
    }

    /** 🔥 기관 사용자 등록 */
    public void createInstitutionUser(InstitutionUserDto dto) {

        // 1️⃣ 기관 선택 필수
        if (dto.getClientId() == null) {
            throw new IllegalArgumentException("기관을 선택해야 합니다.");
        }

        // 2️⃣ 로그인 ID 필수
        if (dto.getLoginId() == null || dto.getLoginId().isBlank()) {
            throw new IllegalArgumentException("로그인 ID는 필수입니다.");
        }

        // 3️⃣ 비밀번호 필수
        if (dto.getPassword() == null || dto.getPassword().isBlank()) {
            throw new IllegalArgumentException("초기 비밀번호는 필수입니다.");
        }

        // 4️⃣ 🔥 기관 내 로그인 ID 중복 체크 (핵심)
        int exists =
            institutionUserMapper.existsByClientIdAndLoginId(
                dto.getClientId(),
                dto.getLoginId()
            );

        if (exists > 0) {
            throw new IllegalArgumentException("이미 존재하는 로그인 ID입니다.");
        }

        // 5️⃣ 기본값 세팅
        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        dto.setRole(
            dto.getRole() == null ? "INSTITUTION_ADMIN" : dto.getRole()
        );
        dto.setStatus(
            dto.getStatus() == null ? "ACTIVE" : dto.getStatus()
        );

        institutionUserMapper.insertInstitutionUser(dto);
    }

    /** 사용자명 + 상태 수정 */
    public void updateInstitutionUser(InstitutionUserDto dto) {

        InstitutionUserDto origin =
            institutionUserMapper.selectById(dto.getInstitutionUserId());

        if (origin == null) {
            throw new IllegalArgumentException("존재하지 않는 사용자");
        }

        // 🔒 변경 불가 항목 강제 유지
        dto.setClientId(origin.getClientId());
        dto.setLoginId(origin.getLoginId());

        // 🔴 status 필수 보장
        if (dto.getStatus() == null) {
            dto.setStatus(origin.getStatus());
        }

        institutionUserMapper.updateInstitutionUser(dto);
    }


    /** 상태 변경 */
    public void changeStatus(Long institutionUserId, String status) {
        institutionUserMapper.updateStatus(institutionUserId, status);
    }

    /** 비밀번호 초기화 (랜덤) */
    public String resetPassword(Long institutionUserId) {

        String tempPassword =
            UUID.randomUUID().toString().substring(0, 10);

        institutionUserMapper.updatePassword(
            institutionUserId,
            passwordEncoder.encode(tempPassword)
        );

        return tempPassword;
    }

    /** 삭제 */
    public void deleteInstitutionUser(Long institutionUserId) {
        institutionUserMapper.deleteById(institutionUserId);
    }
    
    public InstitutionUser validateInstitutionUser(
            Long clientId,
            String loginId,
            String rawPassword
    ) {
        InstitutionUser user =
            institutionUserMapper.selectForLogin(
                Map.of(
                    "clientId", clientId,
                    "loginId", loginId
                )
            );

        if (user == null) {
            return null;
        }

        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            return null;
        }

        return user;
    }
}

