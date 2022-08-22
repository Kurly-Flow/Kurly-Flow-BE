package com.detailretail.kurlyflow.worker.query.application;

import com.detailretail.kurlyflow.order.command.domain.InvoiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PackingService {

  private final InvoiceRepository invoiceRepository;

  public InvoiceResponse getInvoiceForPacking(Long invoiceId) {

    return null;
  }
}
