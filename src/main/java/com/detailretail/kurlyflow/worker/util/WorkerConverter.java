package com.detailretail.kurlyflow.worker.util;

import com.detailretail.kurlyflow.common.vo.Phone;
import com.detailretail.kurlyflow.worker.command.application.LoginResponse;
import com.detailretail.kurlyflow.worker.command.application.SignUpRequest;
import com.detailretail.kurlyflow.worker.command.domain.Worker;

public class WorkerConverter {

  public static Worker toWorker(SignUpRequest signUpRequest) {
    return new Worker(signUpRequest.getName(), Phone.of(signUpRequest.getPhone()),
        PasswordEncrypter.encrypt(signUpRequest.getPassword()));
  }

  public static LoginResponse of(String accessToken, String name) {
    return LoginResponse.builder().accessToken(accessToken).name(name).build();
  }
}
