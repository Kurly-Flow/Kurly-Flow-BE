package com.detailretail.kurlyflow.basket.command.domain;

import com.detailretail.kurlyflow.order.command.domain.Invoice;
import com.detailretail.kurlyflow.worker.command.domain.Worker;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "basket")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Basket {

  @Id
  @Column(name = "id")
  private String id;

  @ManyToOne
  @JoinColumn(name = "worker_id")
  private Worker worker;

  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "basket")
  private List<Invoice> invoices = new ArrayList<>();

  @Column(name = "completed_at")
  private LocalDateTime completedAt;
}