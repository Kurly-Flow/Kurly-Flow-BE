package com.detailretail.kurlyflow.basket.command.domain;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BasketRepository extends JpaRepository<Basket, Long> {

  @Query("SELECT b FROM Basket b JOIN FETCH b.worker bw JOIN FETCH b.invoices bi WHERE bw.id = :workerId AND bi.endAt > :minusTwenty")
  List<Basket> findBasketByWorkerId(Long workerId, LocalDateTime minusTwenty);

}
