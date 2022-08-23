package com.detailretail.kurlyflow.tote.command.application;

import com.detailretail.kurlyflow.batch.command.domain.Batch;
import com.detailretail.kurlyflow.batch.command.domain.BatchRepository;
import com.detailretail.kurlyflow.tote.command.domain.Tote;
import com.detailretail.kurlyflow.tote.command.domain.ToteRepository;
import com.detailretail.kurlyflow.worker.exception.EntityNotFoundException;
import com.detailretail.kurlyflow.worker.presentation.ToteRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ToteCommandService {

  private final ToteRepository toteRepository;
  private final BatchRepository batchRepository;

  public void assignTote(ToteRequest toteRequest, Long workerId) {
    Batch batch = batchRepository.findByCurrentTote(workerId)
        .orElseThrow(EntityNotFoundException::new);
    toteRepository.save(new Tote(toteRequest.getToteId(), batch));
  }

}
