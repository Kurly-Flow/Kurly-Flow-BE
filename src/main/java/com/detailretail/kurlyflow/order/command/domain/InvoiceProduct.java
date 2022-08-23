package com.detailretail.kurlyflow.order.command.domain;

import com.detailretail.kurlyflow.batch.command.domain.Batch;
import com.detailretail.kurlyflow.product.command.domain.Product;
import com.detailretail.kurlyflow.tote.command.domain.Tote;
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
@Table(name = "invoice_product")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InvoiceProduct {

  @Id
  @GeneratedValue
  @Column(name = "id")
  private Long id;

  @ManyToOne
  @JoinColumn(name = "invoice_id")
  private Invoice invoice;

  @ManyToOne
  @JoinColumn(name = "product_id")
  private Product product;

  @ManyToOne
  @JoinColumn(name = "tote_id")
  private Tote tote;

  private Integer quantity;

  @Column(name = "read_at")
  private LocalDateTime readAt;

  @Column(name = "is_barcode_read")
  private Boolean isBarcodeRead = Boolean.FALSE;

  public void readBarcode(Tote tote) {
    if (isBarcodeRead) {
      throw new UnAssignedFieldException();
    }
    this.isBarcodeRead = Boolean.TRUE;
    this.tote= tote;
    this.readAt = LocalDateTime.now();
  }

  public void moveTote(Tote tote) {
    this.tote = tote;
  }
}
