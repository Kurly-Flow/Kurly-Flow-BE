package com.detailretail.kurlyflow.tote.query.application;

import com.detailretail.kurlyflow.batch.Batch;
import com.detailretail.kurlyflow.batch.BatchRepository;
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
public class ToteService {

  private BatchRepository batchRepository;
  private ToteRepository toteRepository;

  public Long getTote(Long workerId) {
    List<Batch> currentTote = batchRepository.findByCurrentTote(workerId);
    double currentToteWeight = currentTote.stream().mapToDouble(
        batch -> batch.getInvoiceProduct().getQuantity() * batch.getInvoiceProduct().getProduct()
            .getWeight()).sum();
    return currentToteWeight > 8.0 ? getNewTote() : currentTote.get(0).getTote().getId();
  }

  public Long getNewTote() {
    Tote tote = toteRepository.findByBatchesNull().orElseThrow(EntityNotFoundException::new);
    return tote.getId();
  }
}
