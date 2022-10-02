package com.detailretail.kurlyflow.admin.command.application;

import com.detailretail.kurlyflow.admin.command.domain.Admin;
import com.detailretail.kurlyflow.admin.command.domain.AdminRepository;
import com.detailretail.kurlyflow.admin.exception.EmployeeNumberConflictException;
import com.detailretail.kurlyflow.admin.util.AdminConverter;
import com.detailretail.kurlyflow.common.vo.EmployeeNumber;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminSignUpService {
    private final AdminRepository adminRepository;

    public Long adminSignUp(AdminSignUpRequest adminSignUpRequest) {
        validateExistEmployeeNumber(adminSignUpRequest.getEmployeeNumber());
        Admin admin = adminRepository.save(AdminConverter.toAdmin(adminSignUpRequest));
        return admin.getId();
    }

    private void validateExistEmployeeNumber(String employeeNumber) {
        adminRepository.findByEmployeeNumber(new EmployeeNumber(employeeNumber)).ifPresent((s) -> {
            throw new EmployeeNumberConflictException();
        });
    }

}
