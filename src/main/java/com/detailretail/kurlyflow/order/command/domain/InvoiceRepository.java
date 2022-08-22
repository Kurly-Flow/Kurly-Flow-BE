package com.detailretail.kurlyflow.order.command.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {


}
