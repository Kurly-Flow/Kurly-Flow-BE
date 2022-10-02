package com.detailretail.kurlyflow.basket.query.application;

import com.detailretail.kurlyflow.basket.command.domain.Basket;
import com.detailretail.kurlyflow.basket.command.domain.BasketRepository;
import com.detailretail.kurlyflow.basket.util.BasketConverter;
import com.detailretail.kurlyflow.order.command.domain.Invoice;
import com.detailretail.kurlyflow.order.command.domain.InvoiceRepository;
import com.detailretail.kurlyflow.worker.exception.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EndService {

  private final BasketRepository basketRepository;
  private final InvoiceRepository invoiceRepository;

  public List<EndCompleteResponse> getEndStatus(Long workerId) {
    List<Basket> baskets = basketRepository.findBasketByWorkerId(workerId,
        LocalDateTime.now().minusMinutes(20L));
    return getEndCompleteResponses(baskets);
  }

  private List<EndCompleteResponse> getEndCompleteResponses(List<Basket> baskets) {
    List<EndCompleteResponse> endCompleteResponses = new ArrayList<>();
    IntStream.range(0, baskets.size()).forEach(basketIdx -> {
      List<Invoice> invoices = invoiceRepository.findByBasketId(baskets.get(basketIdx).getId());
      IntStream.range(0, invoices.size()).forEach(basketInvoiceIdx -> {
        endCompleteResponses.add(
            BasketConverter.ofEnd(baskets.get(basketIdx), invoices.get(basketInvoiceIdx).getId()));
      });
    });
    return endCompleteResponses;
  }

  public EndBasketInvoiceResponse getInvoice(String invoiceId) {
    Invoice invoice = invoiceRepository.findInvoice(invoiceId)
        .orElseThrow(EntityNotFoundException::new);
    return BasketConverter.ofEndInvoice(invoice);
  }
}