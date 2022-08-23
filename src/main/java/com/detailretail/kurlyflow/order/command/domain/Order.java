package com.detailretail.kurlyflow.order.command.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "\"order\"")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {

  @Id
  @Column(name = "id")
  private String id;

  @Embedded
  @AttributeOverrides({
      @AttributeOverride(name = "name", column = @Column(name = "orderer_name")),
      @AttributeOverride(name = "address", column = @Column(name = "orderer_address"))
  })
  private Orderer orderer;

  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "order")
  private List<Invoice> invoices = new ArrayList<>();

  @Column(name = "created_at")
  private LocalDateTime createdAt;
}