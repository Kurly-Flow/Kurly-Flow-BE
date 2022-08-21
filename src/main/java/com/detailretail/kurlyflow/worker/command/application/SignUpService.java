package com.detailretail.kurlyflow.worker.command.application;

import com.detailretail.kurlyflow.common.vo.Phone;
import com.detailretail.kurlyflow.worker.command.domain.Worker;
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

  public Long signUp(SignUpRequest signUpRequest) {
    validateExistPhone(signUpRequest.getPhone());
    Worker worker = workerRepository.save(WorkerConverter.toWorker(signUpRequest));
    return worker.getId();
  }

  public void validateExistPhone(String phone) {
    workerRepository.findByPhone(new Phone(phone)).ifPresent((s) -> {
      throw new PhoneConflictException();
    });
  }
}
