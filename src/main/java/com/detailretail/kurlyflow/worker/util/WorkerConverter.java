package com.detailretail.kurlyflow.worker.util;

import com.detailretail.kurlyflow.common.vo.Phone;
import com.detailretail.kurlyflow.worker.command.application.LoginResponse;
import com.detailretail.kurlyflow.worker.command.application.SignUpRequest;
import com.detailretail.kurlyflow.worker.command.domain.Worker;
import com.detailretail.kurlyflow.worker.query.application.DetailRegionResponse;
import com.detailretail.kurlyflow.worker.query.application.RegionResponse;

public class WorkerConverter {

  public static Worker toWorker(SignUpRequest signUpRequest) {
    return new Worker(signUpRequest.getName(), new Phone(signUpRequest.getPhone()),
        PasswordEncrypter.encrypt(signUpRequest.getPassword()));
  }

  public static LoginResponse ofLogin(String accessToken, String name) {
    return LoginResponse.builder().accessToken(accessToken).name(name).build();
  }

  public static RegionResponse ofRegion(Worker worker, String code) {
    return RegionResponse.builder().code(code).region(worker.getRegion().name()).build();
  }

  public static DetailRegionResponse ofDetailRegion(Worker worker) {
    return DetailRegionResponse.builder().region(worker.getRegion().name()).detail(
        worker.getDetailRegion()).build();
  }
}
