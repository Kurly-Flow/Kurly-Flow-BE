package com.detailretail.kurlyflow.admin.command.application;

import com.detailretail.kurlyflow.admin.command.domain.Admin;
import com.detailretail.kurlyflow.admin.command.domain.AdminRepository;
import com.detailretail.kurlyflow.admin.command.domain.CustomAdminsDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
public class AdminDetailService implements UserDetailsService {
    private final AdminRepository adminRepository;

    @Override
    public CustomAdminsDetails loadUserByUsername(String adminId) {
        Admin admin = adminRepository.findById(Long.valueOf(adminId))
                .orElseThrow(() -> new IllegalArgumentException("찾을 수 없습니다."));
        return CustomAdminsDetails.of(admin);
    }
}
