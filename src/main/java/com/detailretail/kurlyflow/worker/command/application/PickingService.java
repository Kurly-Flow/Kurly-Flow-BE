package com.detailretail.kurlyflow.worker.command.application;

import com.detailretail.kurlyflow.order.command.domain.InvoiceProduct;
import com.detailretail.kurlyflow.order.command.domain.InvoiceProductRepository;
import com.detailretail.kurlyflow.tote.command.domain.Tote;
import com.detailretail.kurlyflow.tote.command.domain.ToteRepository;
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
  private final InvoiceProductRepository invoiceProductRepository;
  private final ToteRepository toteRepository;

  public void readBarcode(Long invoiceProductId, String toteId) {
    InvoiceProduct invoiceProduct = invoiceProductRepository.findById(invoiceProductId)
        .orElseThrow(EntityNotFoundException::new);
    Tote tote = toteRepository.findById(toteId).orElseThrow(EntityNotFoundException::new);
    invoiceProduct.readBarcode(tote);
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
