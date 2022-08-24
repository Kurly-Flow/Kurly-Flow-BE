package com.detailretail.kurlyflow.admin.command.application;

import com.detailretail.kurlyflow.admin.command.domain.Admin;
import com.detailretail.kurlyflow.admin.command.domain.AdminRepository;
import com.detailretail.kurlyflow.admin.command.domain.CustomDetails;
import java.util.List;
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
  public CustomDetails loadUserByUsername(String adminId) {
    Admin admin = adminRepository.findById(Long.valueOf(adminId))
        .orElseThrow(() -> new IllegalArgumentException("찾을 수 없습니다."));
    return CustomDetails.builder().id(admin.getId())
        .authorities(List.of(admin.getAuthority().name())).build();
  }
}
