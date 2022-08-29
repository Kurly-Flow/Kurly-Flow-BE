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
    List<EndCompleteResponse> endCompleteResponses = new ArrayList<>();
    IntStream.range(0, baskets.size()).forEach(basketIdx -> {
      IntStream.range(0, baskets.get(basketIdx).getInvoices().size()).forEach(basketInvoiceIdx -> {
        endCompleteResponses.add(BasketConverter.ofEnd(baskets.get(basketIdx),
            baskets.get(basketIdx).getInvoices().get(basketInvoiceIdx)));
      });
    });
    return endCompleteResponses;
  }

  public EndBasketInvoiceResponse getInvoice(String invoiceId) {
    Invoice invoice = invoiceRepository.findInvoiceWithBasket(invoiceId)
        .orElseThrow(EntityNotFoundException::new);
    return BasketConverter.ofEndInvoice(invoice);
  }
}