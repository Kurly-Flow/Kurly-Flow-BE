package com.detailretail.kurlyflow.order.command.domain;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface InvoiceProductRepository extends JpaRepository<InvoiceProduct, Long> {

  @Query("SELECT ip FROM InvoiceProduct ip WHERE ip.isBarcodeRead = false AND ip.toteId is null")
  List<InvoiceProduct> findTop30NotRead();

  List<InvoiceProduct> findByIdIn(List<Long> invoiceProductIds);
}
