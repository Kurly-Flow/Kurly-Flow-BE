package com.detailretail.kurlyflow.worker.util;

import com.detailretail.kurlyflow.common.vo.Phone;
import com.detailretail.kurlyflow.worker.command.application.AdminCallRequest;
import com.detailretail.kurlyflow.worker.command.application.LoginResponse;
import com.detailretail.kurlyflow.worker.command.application.MultiBatchResponse;
import com.detailretail.kurlyflow.worker.command.application.SignUpRequest;
import com.detailretail.kurlyflow.worker.command.domain.Batch;
import com.detailretail.kurlyflow.worker.command.domain.Worker;
import com.detailretail.kurlyflow.worker.query.application.DetailRegionResponse;
import com.detailretail.kurlyflow.worker.query.application.RegionResponse;

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

  public static RegionResponse ofRegion(Worker worker) {
    return RegionResponse.builder().code(ATTENDANCE_API).region(worker.getRegion().name()).build();
  }

  public static DetailRegionResponse ofDetailRegion(Worker worker) {
    return DetailRegionResponse.builder().region(worker.getRegion().name())
        .detail(worker.getDetailRegion()).build();
  }

  public static MultiBatchResponse ofMultiBatch(Batch batch) {
    return MultiBatchResponse.builder().batchId(batch.getId())
        .name(batch.getInvoiceProduct().getProduct().getName())
        .weight(batch.getInvoiceProduct().getProduct().getWeight())
        .quantity(batch.getInvoiceProduct().getQuantity()).build();
  }
}