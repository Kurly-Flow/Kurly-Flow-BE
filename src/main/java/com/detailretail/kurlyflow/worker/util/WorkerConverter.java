package com.detailretail.kurlyflow.worker.util;

import com.detailretail.kurlyflow.common.vo.Phone;
import com.detailretail.kurlyflow.worker.command.application.AdminCallRequest;
import com.detailretail.kurlyflow.worker.command.application.LoginResponse;
import com.detailretail.kurlyflow.worker.command.application.SignUpRequest;
import com.detailretail.kurlyflow.worker.command.application.WorkingPlaceLoginResponse;
import com.detailretail.kurlyflow.worker.command.domain.Worker;
import com.detailretail.kurlyflow.worker.query.application.DetailRegionResponse;
import com.detailretail.kurlyflow.worker.query.application.InfoResponse;
import com.detailretail.kurlyflow.worker.query.application.RegionResponse;
import java.util.Objects;

public class WorkerConverter {

  private static final String ATTENDANCE_API = "/api/workers/attendance";

  public static Worker toWorker(SignUpRequest signUpRequest) {
    return new Worker(signUpRequest.getName(), new Phone(signUpRequest.getPhone()),
        PasswordEncrypter.encrypt(signUpRequest.getPassword()));
  }

  public static AdminCallRequest toCall(Worker worker) {
    return AdminCallRequest.builder().workerId(worker.getId()).region(worker.getRegion())
        .detailRegion(worker.getDetailRegion()).adminId(worker.getAdminId()).build();
  }

  public static LoginResponse ofLogin(String accessToken, String name) {
    return LoginResponse.builder().accessToken(accessToken).name(name).build();
  }

  public static WorkingPlaceLoginResponse ofWorkingPlaceLogin(String accessToken, Worker worker) {
    return WorkingPlaceLoginResponse.builder().accessToken(accessToken).name(worker.getName())
        .region(worker.getRegion().name()).detailRegion(worker.getDetailRegion()).build();
  }

  public static RegionResponse ofRegion(Worker worker) {
    return RegionResponse.builder().code(ATTENDANCE_API).region(worker.getRegion().name()).build();
  }

  public static DetailRegionResponse ofDetailRegion(Worker worker) {
    return DetailRegionResponse.builder().name(worker.getName())
        .employeeNumber(worker.getRegion().name()).detailRegion(worker.getDetailRegion()).build();
  }

  public static InfoResponse ofInfo(Worker worker) {
    return InfoResponse.builder().name(worker.getName()).phone(worker.getPhone().getNumber())
        .employeeNumber(Objects.isNull(worker.getEmployeeNumber()) ? null
            : worker.getEmployeeNumber().getEmployeeNumber())
        .wishRegion(worker.getWishRegion().name()).region(worker.getRegion().name())
        .detailRegion(worker.getDetailRegion()).isAttended(worker.getIsAttended()).build();
  }

}