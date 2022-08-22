package com.detailretail.kurlyflow.order.query.application;

import com.detailretail.kurlyflow.order.command.domain.Invoice;
import com.detailretail.kurlyflow.order.command.domain.InvoiceRepository;
import com.detailretail.kurlyflow.order.util.InvoiceConverter;
import com.detailretail.kurlyflow.worker.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PackingService {

  private final InvoiceRepository invoiceRepository;

  public InvoiceResponse getInvoiceForPacking(Long invoiceId) {
    Invoice invoice = invoiceRepository.findInvoice(invoiceId)
        .orElseThrow(EntityNotFoundException::new);
    return InvoiceConverter.ofInvoice(invoice);
  }
}
