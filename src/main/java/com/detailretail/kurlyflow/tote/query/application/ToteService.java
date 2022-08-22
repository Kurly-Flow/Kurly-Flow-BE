package com.detailretail.kurlyflow.tote.query.application;

import static com.detailretail.kurlyflow.common.ToteWeightPolicy.MAX_TOTE_WEIGHT;

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
public class ToteService {

  private BatchRepository batchRepository;
  private ToteRepository toteRepository;

  public Long getTote(Long workerId) {
    List<Batch> currentTote = batchRepository.findByCurrentTote(workerId);
    double currentToteWeight = currentTote.stream().mapToDouble(
        batch -> batch.getInvoiceProduct().getQuantity() * batch.getInvoiceProduct().getProduct()
            .getWeight()).sum();
    return currentToteWeight > MAX_TOTE_WEIGHT.getWeight() ? getNewTote() : currentTote.get(0).getTote().getId();
  }

  public Long getNewTote() {
    Tote tote = toteRepository.findByBatchesNull().orElseThrow(EntityNotFoundException::new);
    return tote.getId();
  }
}