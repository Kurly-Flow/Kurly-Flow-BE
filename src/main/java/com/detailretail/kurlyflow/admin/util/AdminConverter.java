package com.detailretail.kurlyflow.admin.util;

import com.detailretail.kurlyflow.admin.command.application.AdminLoginResponse;
import com.detailretail.kurlyflow.admin.command.application.AdminSignUpRequest;
import com.detailretail.kurlyflow.admin.command.domain.Admin;
import com.detailretail.kurlyflow.common.vo.EmployeeNumber;
import com.detailretail.kurlyflow.worker.command.application.LoginResponse;
import com.detailretail.kurlyflow.worker.util.PasswordEncrypter;


public class AdminConverter {

    private static final String ATTENDANCE_API = "/api/admins/attendance";

    public static Admin toAdmin(AdminSignUpRequest adminSignUpRequest) {
        return new Admin(adminSignUpRequest.getName(), new EmployeeNumber(adminSignUpRequest.getEmployeeNumber()),
                PasswordEncrypter.encrypt(adminSignUpRequest.getPassword()));
    }

    public static AdminLoginResponse ofAdminLogin(String accessToken, String name) {
        return AdminLoginResponse.builder().accessToken(accessToken).name(name).build();
    }

}
