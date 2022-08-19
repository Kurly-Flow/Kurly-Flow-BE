package com.detailretail.kurlyflow.worker.command.application;

import com.detailretail.kurlyflow.worker.command.domain.WorkerRepository;
import com.detailretail.kurlyflow.worker.util.WorkerConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class SignUpService {

  private final WorkerRepository workerRepository;

  public void signUp(SignUpRequest signUpRequest) {
    validateExistPhone(signUpRequest.getPhone());
    workerRepository.save(WorkerConverter.toWorker(signUpRequest));
  }

  public void validateExistPhone(String phone) {
    workerRepository.findByPhone(phone).ifPresent((s) -> {
      throw new PhoneConflictException();
    });
  }
}
