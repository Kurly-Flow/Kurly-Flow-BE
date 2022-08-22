package com.detailretail.kurlyflow.worker.command.domain;

import com.detailretail.kurlyflow.order.command.domain.InvoiceProduct;
import com.detailretail.kurlyflow.worker.exception.UnAssignedFieldException;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "batch")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Batch {

  @Id
  @GeneratedValue
  @Column(name = "id")
  private Long id;

  @ManyToOne
  @JoinColumn(name = "worker_id")
  private Worker worker;

  @ManyToOne
  @JoinColumn(name = "invoice_product_id")
  private InvoiceProduct invoiceProduct;

  @ManyToOne
  @JoinColumn(name = "tote_id")
  private Tote tote;

  @Column(name = "is_barcord_read")
  private Boolean isBarcordRead = Boolean.FALSE;

  @Column(name = "read_at")
  private LocalDateTime readAt;

  public Batch(Worker worker, InvoiceProduct invoiceProduct) {
    this.worker = worker;
    this.invoiceProduct = invoiceProduct;
  }

  public void readBarcode(Tote tote) {
    if (isBarcordRead) {
      throw new UnAssignedFieldException();
    }
    this.isBarcordRead = Boolean.TRUE;
    this.tote = tote;
    this.readAt = LocalDateTime.now();
  }

}
