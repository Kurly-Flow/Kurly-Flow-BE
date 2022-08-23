package com.detailretail.kurlyflow.order.command.application;

import com.detailretail.kurlyflow.order.command.domain.Invoice;
import com.detailretail.kurlyflow.order.command.domain.InvoiceRepository;
import com.detailretail.kurlyflow.worker.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class InvoiceConsistencyService {

  private final InvoiceRepository invoiceRepository;


  public void changeInvoiceUnConsistency(String invoiceId) {
    Invoice invoice = invoiceRepository.findInvoice(invoiceId)
        .orElseThrow(EntityNotFoundException::new);
    invoice.changeUnConsistency();
  }
}
