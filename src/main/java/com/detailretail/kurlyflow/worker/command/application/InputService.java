package com.detailretail.kurlyflow.worker.command.application;

import com.detailretail.kurlyflow.common.vo.EmployeeNumber;
import com.detailretail.kurlyflow.common.vo.Region;
import com.detailretail.kurlyflow.worker.command.domain.Worker;
import com.detailretail.kurlyflow.worker.command.domain.WorkerRepository;
import com.detailretail.kurlyflow.worker.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
//이름 다시 정하기
public class InputService {

  private final WorkerRepository workerRepository;

  public void inputEmployeeNumberAndWishRegion(InputRequest inputRequest, Long workerId) {
    Worker worker = workerRepository.findById(workerId).orElseThrow(EntityNotFoundException::new);
    worker.assignEmployeeNumber(EmployeeNumber.of(inputRequest.getEmployeeNumber()));
    worker.assignWishRegion(Region.of(inputRequest.getRegion()));
  }
}
