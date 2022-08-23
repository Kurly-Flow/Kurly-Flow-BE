package com.detailretail.kurlyflow.tote.query.application;

import com.detailretail.kurlyflow.batch.command.domain.Batch;
import com.detailretail.kurlyflow.batch.command.domain.BatchRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ToteQueryService {

  private final BatchRepository batchRepository;

  public String getTote(Long workerId) {
    List<Batch> currentTote = batchRepository.findByCurrentTote(workerId);
    return currentTote.get(0).getTote().getId();
  }

}
