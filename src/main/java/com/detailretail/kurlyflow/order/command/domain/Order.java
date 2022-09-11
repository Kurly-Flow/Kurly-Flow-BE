package com.detailretail.kurlyflow.order.command.domain;

import java.time.LocalDateTime;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
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

  @Column(name = "created_at")
  private LocalDateTime createdAt;
}