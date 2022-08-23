package com.detailretail.kurlyflow.tote.query.application;

import com.detailretail.kurlyflow.batch.command.domain.Batch;
import com.detailretail.kurlyflow.batch.command.domain.BatchRepository;
import com.detailretail.kurlyflow.worker.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ToteQueryService {

  private final BatchRepository batchRepository;

  public String getTote(Long workerId) {
    Batch batch = batchRepository.findByCurrentTote(workerId)
        .orElseThrow(EntityNotFoundException::new);
    return batch.getTotes().get(batch.getTotes().size() - 1).getId();
  }

}
