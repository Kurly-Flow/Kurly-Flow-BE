package com.detailretail.kurlyflow.order.command.domain;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

  @Query("SELECT i FROM Invoice i JOIN FETCH i.invoiceProducts ip JOIN FETCH ip.product")
  Optional<Invoice> findInvoice(@Param(value = "invoiceId") Long invoiceId);
}
