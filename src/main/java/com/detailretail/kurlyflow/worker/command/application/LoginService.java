package com.detailretail.kurlyflow.worker.command.application;

import com.detailretail.kurlyflow.worker.command.domain.Worker;
import com.detailretail.kurlyflow.worker.command.domain.WorkerRepository;
import com.detailretail.kurlyflow.worker.util.WorkerConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class LoginService {

  private final WorkerRepository workerRepository;

  public LoginResponse login(LoginRequest loginRequest) {
    Worker worker = workerRepository.findByPhone(loginRequest.getPhone())
        .orElseThrow(WorkerNotFoundException::new);
    worker.matchPassword(loginRequest.getPassword());
    return WorkerConverter.of(null, worker.getName());
  }
}
