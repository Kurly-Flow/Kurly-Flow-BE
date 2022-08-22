package com.detailretail.kurlyflow.worker.command.application;

import com.detailretail.kurlyflow.common.vo.Phone;
import com.detailretail.kurlyflow.config.jwt.JwtTokenProvider;
import com.detailretail.kurlyflow.worker.command.domain.Worker;
import com.detailretail.kurlyflow.worker.command.domain.WorkerRepository;
import com.detailretail.kurlyflow.worker.exception.WorkerNotFoundException;
import com.detailretail.kurlyflow.worker.util.WorkerConverter;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class LoginService {

  private final WorkerRepository workerRepository;
  private final JwtTokenProvider jwtTokenProvider;

  public LoginResponse login(LoginRequest loginRequest) {
    Worker worker = workerRepository.findByPhone(new Phone(loginRequest.getPhone()))
        .orElseThrow(WorkerNotFoundException::new);
    worker.matchPassword(loginRequest.getPassword());
    worker.updateLoginAt();
    return WorkerConverter.ofLogin(jwtTokenProvider.createToken(String.valueOf(worker.getId()),
        List.of(worker.getAuthority().name())), worker.getName());
  }

  public WorkingPlaceLoginResponse startWork(LoginRequest loginRequest) {
    Worker worker = workerRepository.findByPhone(new Phone(loginRequest.getPhone()))
        .orElseThrow(WorkerNotFoundException::new);
    worker.matchPassword(loginRequest.getPassword());
    worker.startWork();
    return WorkerConverter.ofWorkingPlaceLogin(
        jwtTokenProvider.createToken(String.valueOf(worker.getId()),
            List.of(worker.getAuthority().name())), worker);
  }
}
