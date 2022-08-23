package com.detailretail.kurlyflow.admin.util;

import com.detailretail.kurlyflow.admin.command.application.AdminLoginResponse;
import com.detailretail.kurlyflow.admin.command.application.AdminSignUpRequest;
import com.detailretail.kurlyflow.admin.command.domain.Admin;
import com.detailretail.kurlyflow.admin.query.application.WorkerAttendanceResponse;
import com.detailretail.kurlyflow.admin.query.application.WorkerStatusResponse;
import com.detailretail.kurlyflow.common.vo.EmployeeNumber;
import com.detailretail.kurlyflow.worker.command.domain.Worker;
import com.detailretail.kurlyflow.worker.query.application.DetailRegionResponse;
import com.detailretail.kurlyflow.worker.util.PasswordEncrypter;
import java.util.Objects;


public class AdminConverter {

  private static final String ATTENDANCE_API = "/api/admins/attendance";

  public static Admin toAdmin(AdminSignUpRequest adminSignUpRequest) {
    return new Admin(adminSignUpRequest.getName(),
        new EmployeeNumber(adminSignUpRequest.getEmployeeNumber()),
        PasswordEncrypter.encrypt(adminSignUpRequest.getPassword()));
  }

  public static AdminLoginResponse ofAdminLogin(String accessToken, String name) {
    return AdminLoginResponse.builder().accessToken(accessToken).name(name).build();
  }

  public static WorkerStatusResponse ofStatus(Worker worker) {
    return WorkerStatusResponse.builder().employeeNumber(
            Objects.isNull(worker.getEmployeeNumber()) ? null
                : worker.getEmployeeNumber().getEmployeeNumber()).name(worker.getName())
        .detailRegion(Objects.isNull(worker.getDetailRegion()) ? null : worker.getDetailRegion())
        .build();
  }

  public static WorkerAttendanceResponse ofAttendance(Worker worker) {
    return WorkerAttendanceResponse.builder().employeeNumber(
            Objects.isNull(worker.getEmployeeNumber()) ? null
                : worker.getEmployeeNumber().getEmployeeNumber()).name(worker.getName())
        .isAttended(worker.getIsAttended()).build();
  }

  public static DetailRegionResponse ofDetailRegion(Worker worker) {
    return DetailRegionResponse.builder().name(worker.getName())
        .employeeNumber(worker.getEmployeeNumber().getEmployeeNumber())
        .detailRegion(worker.getDetailRegion()).build();
  }
}
