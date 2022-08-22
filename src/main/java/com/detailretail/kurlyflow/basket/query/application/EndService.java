package com.detailretail.kurlyflow.order.query.application;

import com.detailretail.kurlyflow.basket.command.domain.Basket;
import com.detailretail.kurlyflow.basket.command.domain.BasketRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EndService {

  private final BasketRepository basketRepository;

  public void getInvoices(Long workerId) {
    List<Basket> baskets = basketRepository.findByWorkerId(workerId);
  }
}
