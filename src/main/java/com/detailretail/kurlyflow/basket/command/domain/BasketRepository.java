package com.detailretail.kurlyflow.basket.command.domain;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BasketRepository extends JpaRepository<Basket, Long> {

  @Query(value = "select distinct b from basket as b join worker as w on b.worker_id=w.id join invoice as i on b.invoice_id= i.id where w.id =:workerId and i.end_at > :minusTwenty", nativeQuery = true)
  List<Basket> findBasketByWorkerId(Long workerId, LocalDateTime minusTwenty);

}
