package com.detailretail.kurlyflow.worker.util;

import com.detailretail.kurlyflow.common.vo.Phone;
import com.detailretail.kurlyflow.worker.command.application.AdminCallRequest;
import com.detailretail.kurlyflow.worker.command.application.LoginResponse;
import com.detailretail.kurlyflow.worker.command.application.MultiBatchResponse;
import com.detailretail.kurlyflow.worker.command.application.MultiBatchResponse.BatchResponse;
import com.detailretail.kurlyflow.worker.command.application.SignUpRequest;
import com.detailretail.kurlyflow.worker.command.application.WorkingPlaceLoginResponse;
import com.detailretail.kurlyflow.worker.command.domain.Batch;
import com.detailretail.kurlyflow.worker.command.domain.Worker;
import com.detailretail.kurlyflow.worker.query.application.DetailRegionResponse;
import com.detailretail.kurlyflow.worker.query.application.RegionResponse;
import java.util.List;

public class WorkerConverter {

  private static final String ATTENDANCE_API = "/api/workers/attendance";
  private static final double MAX_TOTE_WEIGHT = 4.7;

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
    return DetailRegionResponse.builder().region(worker.getRegion().name())
        .detail(worker.getDetailRegion()).build();
  }

  public static BatchResponse ofBatch(Batch batch) {
    return BatchResponse.builder().batchId(batch.getId())
        .name(batch.getInvoiceProduct().getProduct().getName())
        .quantity(batch.getInvoiceProduct().getQuantity())
        .weight(batch.getInvoiceProduct().getProduct().getWeight()).build();
  }

  public static MultiBatchResponse ofMulti(List<BatchResponse> batchResponses) {
    return MultiBatchResponse.builder().recommendToteCount(calculateTote(batchResponses))
        .batchResponses(batchResponses).build();
  }

  private static Integer calculateTote(List<BatchResponse> batchResponses) {
    double sum = batchResponses.stream()
        .mapToDouble(batchResponse -> batchResponse.getWeight() * batchResponse.getQuantity())
        .sum();
    return (int) Math.round(sum / MAX_TOTE_WEIGHT);
  }
}