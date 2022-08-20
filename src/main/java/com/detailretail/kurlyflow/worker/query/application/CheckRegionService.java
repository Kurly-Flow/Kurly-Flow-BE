package com.detailretail.kurlyflow.worker.query.application;

import com.detailretail.kurlyflow.worker.command.domain.Worker;
import com.detailretail.kurlyflow.worker.command.domain.WorkerRepository;
import com.detailretail.kurlyflow.worker.exception.WorkerNotFoundException;
import com.detailretail.kurlyflow.worker.util.WorkerConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CheckRegionService {

  private final WorkerRepository workerRepository;

  public RegionResponse checkRegion(Long workerId) {
    Worker worker = workerRepository.findById(workerId).orElseThrow(WorkerNotFoundException::new);
    worker.canCheckRegion();
    return WorkerConverter.ofRegion(worker);
  }

  public DetailRegionResponse checkDetailRegion(Long workerId) {
    Worker worker = workerRepository.findById(workerId).orElseThrow(WorkerNotFoundException::new);
    worker.canCheckDetailRegion();
    return WorkerConverter.ofDetailRegion(worker);
  }
}
