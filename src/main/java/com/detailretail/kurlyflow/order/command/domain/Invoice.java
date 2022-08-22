package com.detailretail.kurlyflow.order.command.domain;

import com.detailretail.kurlyflow.basket.command.domain.Basket;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "invoice")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Invoice {

  @Id
  @GeneratedValue
  @Column(name = "id")
  private Long id;

  @ManyToOne
  @JoinColumn(name = "order_id")
  private Order order;

  @ManyToOne
  @JoinColumn(name = "basket_id")
  private Basket basket;

  @Column(name = "is_consistency")
  private Boolean isConsistency = Boolean.TRUE;

  @Column(name = "end_at")
  private LocalDateTime endAt;

  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "invoice")
  private List<InvoiceProduct> invoiceProducts = new ArrayList<>();

  public void changeUnConsistency() {
    this.isConsistency = Boolean.FALSE;
  }
}
