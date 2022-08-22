package com.detailretail.kurlyflow.worker.command.application;

import com.detailretail.kurlyflow.worker.command.domain.Batch;
import com.detailretail.kurlyflow.worker.command.domain.BatchRepository;
import com.detailretail.kurlyflow.worker.command.domain.Tote;
import com.detailretail.kurlyflow.worker.command.domain.ToteRepository;
import com.detailretail.kurlyflow.worker.command.domain.WorkerRepository;
import com.detailretail.kurlyflow.worker.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PickingService {

  private final WorkerRepository workerRepository;
  private final BatchRepository batchRepository;
  private final ToteRepository toteRepository;

  public void readBarcode(Long batchId, Long toteId) {
    Batch batch = batchRepository.findById(batchId).orElseThrow(EntityNotFoundException::new);
    Tote tote = toteRepository.findById(toteId).orElseThrow(EntityNotFoundException::new);
    batch.readBarcode(tote);
  }

  public void workingToggle(Long workerId) {
    workerRepository.findById(workerId).ifPresentOrElse(worker -> {
      if (worker.getIsWorked()) {
        worker.breakWork();
      } else {
        worker.startWork();
      }
    }, () -> new EntityNotFoundException());
  }
}
