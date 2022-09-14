package com.detailretail.kurlyflow.tote.query.application;

import com.detailretail.kurlyflow.batch.command.domain.Batch;
import com.detailretail.kurlyflow.batch.command.domain.BatchRepository;
import com.detailretail.kurlyflow.tote.command.domain.Tote;
import com.detailretail.kurlyflow.tote.command.domain.ToteRepository;
import com.detailretail.kurlyflow.worker.exception.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ToteQueryService {

  private final BatchRepository batchRepository;
  private final ToteRepository toteRepository;

  public String getTote(Long workerId) {
    Batch batch = batchRepository.findByCurrentTote(workerId)
        .orElseThrow(EntityNotFoundException::new);
    List<Tote> totes = toteRepository.findByBatchId(batch.getId());
    return totes.get(totes.size() - 1).getId();
  }

}
