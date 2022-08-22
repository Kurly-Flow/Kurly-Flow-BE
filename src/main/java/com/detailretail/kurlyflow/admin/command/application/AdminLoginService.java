package com.detailretail.kurlyflow.admin.command.application;

import com.detailretail.kurlyflow.admin.command.domain.Admin;
import com.detailretail.kurlyflow.admin.command.domain.AdminRepository;
import com.detailretail.kurlyflow.admin.exception.AdminNotFoundException;
import com.detailretail.kurlyflow.admin.util.AdminConverter;
import com.detailretail.kurlyflow.common.vo.EmployeeNumber;
import com.detailretail.kurlyflow.config.jwt.JwtTokenProvider;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminLoginService {

    private final AdminRepository adminRepository;
    private final JwtTokenProvider jwtTokenProvider;


    public AdminLoginResponse adminLogin(AdminLoginRequest adminLoginRequest){
        Admin admin = adminRepository.findByEmployeeNumber(new EmployeeNumber(adminLoginRequest.getEmployeeNumber()))
                .orElseThrow(AdminNotFoundException::new);
        admin.matchPassword(adminLoginRequest.getPassword());
        return AdminConverter.ofAdminLogin(jwtTokenProvider.createToken(String.valueOf(admin.getId()),
                List.of(admin.getAuthority().name())), admin.getName());
    }
}
